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
package net.sf.l2j.gameserver.geoengine.geodata;

/**
 * @author Hasha
 */
public final class GeoStructure
{
	public static final int TILE_X_MIN = 16;
	public static final int TILE_X_MAX = 26;
	public static final int TILE_Y_MIN = 10;
	public static final int TILE_Y_MAX = 25;
	public static final int TILE_SIZE = 32768;
	public static final int WORLD_X_MIN = (TILE_X_MIN - 20) * TILE_SIZE;
	public static final int WORLD_X_MAX = (TILE_X_MAX - 19) * TILE_SIZE;
	public static final int WORLD_Y_MIN = (TILE_Y_MIN - 18) * TILE_SIZE;
	public static final int WORLD_Y_MAX = (TILE_Y_MAX - 17) * TILE_SIZE;
	public static final int WORLD_WIDTH = Math.abs(WORLD_X_MIN - WORLD_X_MAX);
	public static final int WORLD_HEIGHT = Math.abs(WORLD_Y_MIN - WORLD_Y_MAX);

	// cells
	public static final byte CELL_FLAG_E = 1 << 0;
	public static final byte CELL_FLAG_W = 1 << 1;
	public static final byte CELL_FLAG_S = 1 << 2;
	public static final byte CELL_FLAG_N = 1 << 3;
	public static final byte CELL_FLAG_SE = 1 << 4;
	public static final byte CELL_FLAG_SW = 1 << 5;
	public static final byte CELL_FLAG_NE = 1 << 6;
	public static final byte CELL_FLAG_NW = (byte) (1 << 7);
	public static final byte CELL_FLAG_S_AND_E = CELL_FLAG_S | CELL_FLAG_E;
	public static final byte CELL_FLAG_S_AND_W = CELL_FLAG_S | CELL_FLAG_W;
	public static final byte CELL_FLAG_N_AND_E = CELL_FLAG_N | CELL_FLAG_E;
	public static final byte CELL_FLAG_N_AND_W = CELL_FLAG_N | CELL_FLAG_W;
	
	public static final int CELL_SIZE = 16;
	public static final int CELL_HEIGHT = 8;
	public static final int CELL_IGNORE_HEIGHT = CELL_HEIGHT * 6;
	
	// blocks
	public static final byte TYPE_FLAT_L2J_L2OFF = 0;
	public static final byte TYPE_FLAT_L2D = (byte) 0xD0;
	public static final byte TYPE_COMPLEX_L2J = 1;
	public static final byte TYPE_COMPLEX_L2OFF = 0x40;
	public static final byte TYPE_COMPLEX_L2D = (byte) 0xD1;
	public static final byte TYPE_MULTILAYER_L2J = 2;
	// public static final byte TYPE_MULTILAYER_L2OFF = 0x41; // officially not does exist, is anything above complex block (0x41 - 0xFFFF)
	public static final byte TYPE_MULTILAYER_L2D = (byte) 0xD2;
	
	public static final int BLOCK_CELLS_X = 8;
	public static final int BLOCK_CELLS_Y = 8;
	public static final int BLOCK_CELLS = BLOCK_CELLS_X * BLOCK_CELLS_Y;
	
	// regions
	public static final int REGION_BLOCKS_X = 256;
	public static final int REGION_BLOCKS_Y = 256;
	public static final int REGION_BLOCKS = REGION_BLOCKS_X * REGION_BLOCKS_Y;
	
	public static final int REGION_CELLS_X = REGION_BLOCKS_X * BLOCK_CELLS_X;
	public static final int REGION_CELLS_Y = REGION_BLOCKS_Y * BLOCK_CELLS_Y;
	
	// global geodata
	public static final int GEO_REGIONS_X = (TILE_X_MAX - TILE_X_MIN + 1);
	public static final int GEO_REGIONS_Y = (TILE_Y_MAX - TILE_Y_MIN + 1);
	
	public static final int GEO_BLOCKS_X = GEO_REGIONS_X * REGION_BLOCKS_X;
	public static final int GEO_BLOCKS_Y = GEO_REGIONS_Y * REGION_BLOCKS_Y;
	
	public static final int GEO_CELLS_X = GEO_BLOCKS_X * BLOCK_CELLS_X;
	public static final int GEO_CELLS_Y = GEO_BLOCKS_Y * BLOCK_CELLS_Y;
}