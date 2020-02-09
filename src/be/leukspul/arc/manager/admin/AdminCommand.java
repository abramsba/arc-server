package be.leukspul.arc.manager.admin;

import be.leukspul.data.ecs.Entity;

import java.util.Arrays;
import java.util.List;

public abstract class AdminCommand {

    public AdminCommand(String input) {
        String [] parts = input.split(" ");
        Arguments = Arrays.asList(parts);
    }

    protected int IntArg(int i) {
        return Integer.parseInt(Arguments.get(i));
    }

    protected double DoubleArg(int i) {
        return Double.parseDouble(Arguments.get(i));
    }

    protected boolean BoolArg(int i) {
        return Boolean.parseBoolean(Arguments.get(i));
    }

    protected String StringArg(int i) {
        return Arguments.get(i);
    }

    protected int args() {
        return Arguments.size();
    }

    public abstract void execute(Entity admin);

    List<String> Arguments;

}
