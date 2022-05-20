package org.demo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.approvaltests.Approvals.verify;
import codingdojo.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.sfvl.doctesting.junitextension.ApprovalsExtension;
import org.sfvl.doctesting.junitextension.HtmlPageExtension;
import org.sfvl.doctesting.junitextension.SimpleApprovalsExtension;

@ExtendWith(HtmlPageExtension.class)
@DisplayName("Lift System")
public class LiftSystemTest {
    @RegisterExtension
    static ApprovalsExtension doc = new SimpleApprovalsExtension();

    @Test
    @DisplayName("Single Lift - Stays on Ground Floor")
    public void singleLiftStaysOnGroundFloor() {
        var starting_floor = 1;
        writeLine("One lift on floor %s ", starting_floor);
        Lift liftA = new Lift("A", starting_floor);
        LiftSystem lifts = new LiftSystem(
                Arrays.asList(0, 1),
                Collections.singletonList(liftA),
                Collections.emptyList());
        write(lifts);
        writeLine("\\... one tick of time passes ... ");
        lifts.tick();
        write(lifts);
    }

    private void writeLine(String formattedText, Object... args) {
        doc.write(String.format(formattedText + "\n", args));
    }

    private void write(LiftSystem lifts) {
        doc.write(new AsciiDocLiftSystemPrinter().print(lifts) + "\n");
    }
}
