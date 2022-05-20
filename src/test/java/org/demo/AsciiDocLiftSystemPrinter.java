package org.demo;

import codingdojo.Call;
import codingdojo.Lift;
import codingdojo.LiftSystem;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.valueOf;

/**
 * Prints the state of a LiftSystem using ASCII art.
 */
public class AsciiDocLiftSystemPrinter {
    public String print(LiftSystem liftSystem) {
        var liftsPrintout = this.print(liftSystem, new LiftAndDoorPrinter());
        return liftsPrintout;
    }

    public String printWithoutDoors(LiftSystem liftSystem) {
        return this.print(liftSystem, new SimpleLiftPrinter());
    }

    public String print(LiftSystem liftSystem, LiftPrinter liftPrinter) {
        StringBuilder sb = new StringBuilder();
        String header = """           
[%autowidth]
|===
|Floor | Call | Lift A
                
""";
        String footer = """
|===
""";
        sb.append(header);
        int floorLength = calculateFloorLength(liftSystem.getFloorsInDescendingOrder());
        for (int floor : liftSystem.getFloorsInDescendingOrder()) {
            // if the floor number doesn't use all the characters, pad with whitespace
            String floorPadding = getWhitespace(floorLength - valueOf(floor).length());
            sb.append("|");
            sb.append(floorPadding);
            sb.append(floor);

            String calls = liftSystem.getCallsForFloor(floor)
                    .stream()
                    .map(this::printCallDirection)
                    .collect(Collectors.joining(""));
            // if there are less than 2 calls on a floor we add padding to keep everything aligned
            String callPadding = getWhitespace(2 - calls.length());
            sb.append(" | ");
            sb.append(calls);
            sb.append(callPadding);

            sb.append(" | ");
            String lifts = liftSystem.getLifts()
                    .stream()
                    .map((Lift l) -> liftPrinter.printLiftForFloor(l, floor))
                    .collect(Collectors.joining(" "));
            sb.append(lifts);
            sb.append('\n');
        }
        sb.append(footer);
        return sb.toString();
    }

    private String printCallDirection(Call call) {
        switch (call.getDirection()) {
            case DOWN:
                return "v";
            case UP:
                return "^";
            default:
                return " "; // should be unreachable
        }
    }

    public static String getWhitespace(int length) {
        return " ".repeat(Math.max(0, length));
    }

    private int calculateFloorLength(List<Integer> floors) {
        if (floors.isEmpty()) {
            throw new IllegalArgumentException("Must have at least one floor");
        }
        int highestFloor = Collections.max(floors);
        int lowestFloor = Collections.min(floors);
        int highestFloorNameLength = valueOf(highestFloor).length();
        int lowestFloorNameLength = valueOf(lowestFloor).length();
        return Math.max(highestFloorNameLength, lowestFloorNameLength);
    }
}
