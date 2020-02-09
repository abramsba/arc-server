/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package net.sf.l2j.gameserver.geoengine;

import be.leukspul.arc.server.GameServer;
import be.leukspul.math.Vector3;
import net.sf.l2j.commons.lang.StringUtil;
import net.sf.l2j.gameserver.geoengine.geodata.GeoLocation;
import net.sf.l2j.gameserver.geoengine.pathfinding.Node;
import net.sf.l2j.gameserver.geoengine.pathfinding.NodeBuffer;
import net.sf.l2j.gameserver.model.Location;

import java.util.*;

/**
 * @author Hasha
 */
public final class GeoEnginePathfinding extends GeoEngine
{
	// pre-allocated buffers
	private BufferHolder[] _buffers;

	// pathfinding statistics
	private int _findSuccess = 0;
	private int _findFails = 0;
	private int _postFilterPlayableUses = 0;
	private int _postFilterUses = 0;
	private long _postFilterElapsed = 0;

	public boolean ready() {
		return ready;
	}
	private boolean ready;
	
	public GeoEnginePathfinding()
	{
		super();
		
		String[] array = GameServer.Cfg.Geo.Buffers.split(";"); // Config.PATHFIND_BUFFERS.split(";");
		_buffers = new BufferHolder[array.length];
		
		int count = 0;
		for (int i = 0; i < array.length; i++)
		{
			String buf = array[i];
			String[] args = buf.split("x");
			
			try
			{
				int size = Integer.parseInt(args[1]);
				count += size;
				_buffers[i] = new BufferHolder(Integer.parseInt(args[0]), size);
			}
			catch (Exception e)
			{
				//_log.warning("GeoEnginePathfinding: Can not load buffer setting: " + buf);
			}
		}
		
		//_log.info("GeoEnginePathfinding: Loaded " + count + " node buffers.");
		ready = true;
	}

	public Queue<Vector3> findPath(Vector3 start, Vector3 end) {
		Queue<Vector3> vectors = new LinkedList<>();
		List<Location> locations = findPath((int)start.x(), (int)start.y(), (int)start.z(), (int)end.x(), (int)end.y(), (int)end.z(), true);
		if (locations == null) {
			return null;
		}
		for (Location loc : locations) {
			vectors.add(new Vector3(loc.getX(), loc.getY(), loc.getZ()));
		}
		return vectors;
	}

	@Override
	public List<Location> findPath(int ox, int oy, int oz, int tx, int ty, int tz, boolean playable)
	{
		// get origin and check existing geo coords
		int gox = getGeoX(ox);
		int goy = getGeoY(oy);
		if (!hasGeoPos(gox, goy))
			return null;
		
		short goz = getHeightNearest(gox, goy, oz);
		
		// get target and check existing geo coords
		int gtx = getGeoX(tx);
		int gty = getGeoY(ty);
		if (!hasGeoPos(gtx, gty))
			return null;
		
		short gtz = getHeightNearest(gtx, gty, tz);
		
		// Prepare buffer for pathfinding calculations
		NodeBuffer buffer = getBuffer(64 + (2 * Math.max(Math.abs(gox - gtx), Math.abs(goy - gty))), playable);
		if (buffer == null)
			return null;
		
		// find path
		List<Location> path = null;
		try
		{
			Node result = buffer.findPath(gox, goy, goz, gtx, gty, gtz);
			
			if (result == null)
			{
				_findFails++;
				return null;
			}
			
			path = constructPath(result);
		}
		catch (Exception e)
		{
			_log.warning(e.getMessage());
			_findFails++;
			return null;
		}
		finally
		{
			buffer.free();
			_findSuccess++;
		}
		
		// check path
		if (path.size() < 3)
			return path;
		
		// log data
		long timeStamp = System.currentTimeMillis();
		_postFilterUses++;
		if (playable)
			_postFilterPlayableUses++;
		
		// get path list iterator
		ListIterator<Location> point = path.listIterator();
		
		// get node A (origin)
		int nodeAx = gox;
		int nodeAy = goy;
		short nodeAz = goz;
		
		// get node B
		GeoLocation nodeB = (GeoLocation) point.next();
		
		// iterate thought the path to optimize it
		while (point.hasNext())
		{
			// get node C
			GeoLocation nodeC = (GeoLocation) path.get(point.nextIndex());
			
			// check movement from node A to node C
			GeoLocation loc = checkMove(nodeAx, nodeAy, nodeAz, nodeC.getGeoX(), nodeC.getGeoY(), nodeC.getZ());
			if (loc.getGeoX() == nodeC.getGeoX() && loc.getGeoY() == nodeC.getGeoY())
			{
				// can move from node A to node C
				
				// remove node B
				point.remove();

			}
			else
			{
				// can not move from node A to node C
				
				// set node A (node B is part of path, update A coordinates)
				nodeAx = nodeB.getGeoX();
				nodeAy = nodeB.getGeoY();
				nodeAz = (short) nodeB.getZ();
			}
			
			// set node B
			nodeB = (GeoLocation) point.next();
		}
		
		// log data
		_postFilterElapsed += System.currentTimeMillis() - timeStamp;
		
		return path;
	}
	
	/**
	 * Create list of node locations as result of calculated buffer node tree.
	 * @param target : the entry point
	 * @return List<NodeLoc> : list of node location
	 */
	private static final List<Location> constructPath(Node target)
	{
		// create empty list
		LinkedList<Location> list = new LinkedList<>();
		
		// set direction X/Y
		int dx = 0;
		int dy = 0;
		
		// get target parent
		Node parent = target.getParent();
		
		// while parent exists
		while (parent != null)
		{
			// get parent <> target direction X/Y
			final int nx = parent.getLoc().getGeoX() - target.getLoc().getGeoX();
			final int ny = parent.getLoc().getGeoY() - target.getLoc().getGeoY();
			
			// direction has changed?
			if (dx != nx || dy != ny)
			{
				// add node to the beginning of the list
				list.addFirst(target.getLoc());
				
				// update direction X/Y
				dx = nx;
				dy = ny;
			}
			
			// move to next node, set target and get its parent
			target = parent;
			parent = target.getParent();
		}
		
		// return list
		return list;
	}
	
	/**
	 * Provides optimize selection of the buffer. When all pre-initialized buffer are locked, creates new buffer and log this situation.
	 * @param size : pre-calculated minimal required size
	 * @param playable : moving object is playable?
	 * @return NodeBuffer : buffer
	 */
	private final NodeBuffer getBuffer(int size, boolean playable)
	{
		NodeBuffer current = null;
		for (BufferHolder holder : _buffers)
		{
			// Find proper size of buffer
			if (holder._size < size)
				continue;
			
			// Find unlocked NodeBuffer
			for (NodeBuffer buffer : holder._buffer)
			{
				if (!buffer.isLocked())
					continue;
				
				holder._uses++;
				if (playable)
					holder._playableUses++;
				
				holder._elapsed += buffer.getElapsedTime();
				return buffer;
			}
			
			// NodeBuffer not found, allocate temporary buffer
			current = new NodeBuffer(holder._size);
			current.isLocked();
			
			holder._overflows++;
			if (playable)
				holder._playableOverflows++;
		}
		
		return current;
	}
	
	/**
	 * NodeBuffer container with specified size and count of separate buffers.
	 */
	private static final class BufferHolder
	{
		final int _size;
		final int _count;
		ArrayList<NodeBuffer> _buffer;
		
		// statistics
		int _playableUses = 0;
		int _uses = 0;
		int _playableOverflows = 0;
		int _overflows = 0;
		long _elapsed = 0;
		
		public BufferHolder(int size, int count)
		{
			_size = size;
			_count = count;
			_buffer = new ArrayList<>(count);
			
			for (int i = 0; i < count; i++)
				_buffer.add(new NodeBuffer(size));
		}
		
		@Override
		public String toString()
		{
			final StringBuilder sb = new StringBuilder(100);
			
			StringUtil.append(sb, "Buffer ", String.valueOf(_size), "x", String.valueOf(_size), ": count=", String.valueOf(_count), " uses=", String.valueOf(_playableUses), "/", String.valueOf(_uses));
			
			if (_uses > 0)
				StringUtil.append(sb, " total/avg(ms)=", String.valueOf(_elapsed), "/", String.format("%1.2f", (double) _elapsed / _uses));
			
			StringUtil.append(sb, " ovf=", String.valueOf(_playableOverflows), "/", String.valueOf(_overflows));
			
			return sb.toString();
		}
	}
	
	@Override
	public List<String> getStat()
	{
		List<String> list = new ArrayList<>();
		
		for (BufferHolder buffer : _buffers)
			list.add(buffer.toString());
		
		list.add("Use: playable=" + String.valueOf(_postFilterPlayableUses) + " non-playable=" + String.valueOf(_postFilterUses - _postFilterPlayableUses));
		
		if (_postFilterUses > 0)
			list.add("Time (ms): total=" + String.valueOf(_postFilterElapsed) + " avg=" + String.format("%1.2f", (double) _postFilterElapsed / _postFilterUses));
		
		list.add("Pathfind: success=" + String.valueOf(_findSuccess) + ", fail=" + String.valueOf(_findFails));
		
		return list;
	}
}