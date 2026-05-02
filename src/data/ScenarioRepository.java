package data;

import model.Dimension;
import model.Metric;
import model.Scenario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScenarioRepository {

    private static final Map<String, Scenario> scenarios = new HashMap<>();

    static {
        buildEducationScenarios();
        buildHealthScenarios();
    }

    private static void buildEducationScenarios() {

        // Scenario C
        Scenario scenC = new Scenario("C", "Scenario C — Team Alpha", "Education", "Product Quality");

        Dimension usability = new Dimension("Usability", 25);
        usability.addMetric(new Metric("SUS score",       50, true,  0, 100, "points"));
        usability.addMetric(new Metric("Onboarding time", 50, false, 0,  60, "min"));
        scenC.addDimension(usability);

        Dimension perfEff = new Dimension("Performance Efficiency", 20);
        perfEff.addMetric(new Metric("Video start time", 50, false, 0,  15, "sec"));
        perfEff.addMetric(new Metric("Concurrent exams", 50, true,  0, 600, "users"));
        scenC.addDimension(perfEff);

        Dimension accessibility = new Dimension("Accessibility", 20);
        accessibility.addMetric(new Metric("WCAG compliance",     50, true, 0, 100, "%"));
        accessibility.addMetric(new Metric("Screen reader score", 50, true, 0, 100, "%"));
        scenC.addDimension(accessibility);

        Dimension reliability = new Dimension("Reliability", 20);
        reliability.addMetric(new Metric("Uptime", 50, true,  95, 100, "%"));
        reliability.addMetric(new Metric("MTTR",   50, false,  0, 120, "min"));
        scenC.addDimension(reliability);

        Dimension funcSuit = new Dimension("Functional Suitability", 15);
        funcSuit.addMetric(new Metric("Feature completion",     50, true, 0, 100, "%"));
        funcSuit.addMetric(new Metric("Assignment submit rate", 50, true, 0, 100, "%"));
        scenC.addDimension(funcSuit);

        scenC.getDimensions().get(0).getMetrics().get(0).setRawValue(89);
        scenC.getDimensions().get(0).getMetrics().get(1).setRawValue(5);
        scenC.getDimensions().get(1).getMetrics().get(0).setRawValue(2);
        scenC.getDimensions().get(1).getMetrics().get(1).setRawValue(450);
        scenC.getDimensions().get(2).getMetrics().get(0).setRawValue(88);
        scenC.getDimensions().get(2).getMetrics().get(1).setRawValue(92);
        scenC.getDimensions().get(3).getMetrics().get(0).setRawValue(99.2);
        scenC.getDimensions().get(3).getMetrics().get(1).setRawValue(15);
        scenC.getDimensions().get(4).getMetrics().get(0).setRawValue(95);
        scenC.getDimensions().get(4).getMetrics().get(1).setRawValue(91);
        scenarios.put("C", scenC);

        // Scenario D
        Scenario scenD = new Scenario("D", "Scenario D — Team Beta", "Education", "Product Quality");

        Dimension usability2 = new Dimension("Usability", 25);
        usability2.addMetric(new Metric("Task success rate",   50, true,  0, 100, "%"));
        usability2.addMetric(new Metric("Error recovery time", 50, false, 0,  30, "sec"));
        scenD.addDimension(usability2);

        Dimension security = new Dimension("Security", 25);
        security.addMetric(new Metric("Login failure rate", 50, false, 0, 100, "%"));
        security.addMetric(new Metric("Data breach score",  50, false, 0, 100, "points"));
        scenD.addDimension(security);

        Dimension maintainability = new Dimension("Maintainability", 20);
        maintainability.addMetric(new Metric("Code coverage",   50, true,  0, 100, "%"));
        maintainability.addMetric(new Metric("Tech debt ratio", 50, false, 0, 100, "%"));
        scenD.addDimension(maintainability);

        Dimension portability = new Dimension("Portability", 15);
        portability.addMetric(new Metric("Browser compatibility", 50, true, 0, 100, "%"));
        portability.addMetric(new Metric("Mobile score",          50, true, 0, 100, "points"));
        scenD.addDimension(portability);

        Dimension reliability2 = new Dimension("Reliability", 15);
        reliability2.addMetric(new Metric("Mean time between failures", 50, true,  0, 720, "hrs"));
        reliability2.addMetric(new Metric("Recovery time",              50, false, 0,  60, "min"));
        scenD.addDimension(reliability2);

        scenD.getDimensions().get(0).getMetrics().get(0).setRawValue(82);
        scenD.getDimensions().get(0).getMetrics().get(1).setRawValue(8);
        scenD.getDimensions().get(1).getMetrics().get(0).setRawValue(5);
        scenD.getDimensions().get(1).getMetrics().get(1).setRawValue(12);
        scenD.getDimensions().get(2).getMetrics().get(0).setRawValue(78);
        scenD.getDimensions().get(2).getMetrics().get(1).setRawValue(18);
        scenD.getDimensions().get(3).getMetrics().get(0).setRawValue(91);
        scenD.getDimensions().get(3).getMetrics().get(1).setRawValue(88);
        scenD.getDimensions().get(4).getMetrics().get(0).setRawValue(600);
        scenD.getDimensions().get(4).getMetrics().get(1).setRawValue(10);
        scenarios.put("D", scenD);
    }

    private static void buildHealthScenarios() {

        // Scenario A
        Scenario scenA = new Scenario("A", "Scenario A — Sprint Review", "Health", "Process Quality");

        Dimension sprintEff = new Dimension("Sprint Efficiency", 30);
        sprintEff.addMetric(new Metric("Velocity",          50, true, 0, 200, "points"));
        sprintEff.addMetric(new Metric("Burndown accuracy", 50, true, 0, 100, "%"));
        scenA.addDimension(sprintEff);

        Dimension codeQuality = new Dimension("Code Quality", 30);
        codeQuality.addMetric(new Metric("Defect density",   50, false, 0,  50, "bugs/KLOC"));
        codeQuality.addMetric(new Metric("Code review rate", 50, true,  0, 100, "%"));
        scenA.addDimension(codeQuality);

        Dimension teamCollab = new Dimension("Team Collaboration", 25);
        teamCollab.addMetric(new Metric("PR merge time",    50, false, 0,  48, "hrs"));
        teamCollab.addMetric(new Metric("Meeting coverage", 50, true,  0, 100, "%"));
        scenA.addDimension(teamCollab);

        Dimension delivery = new Dimension("Delivery", 15);
        delivery.addMetric(new Metric("On-time delivery rate", 50, true, 0, 100, "%"));
        delivery.addMetric(new Metric("Deployment frequency",  50, true, 0,  30, "deploys/mo"));
        scenA.addDimension(delivery);

        scenA.getDimensions().get(0).getMetrics().get(0).setRawValue(160);
        scenA.getDimensions().get(0).getMetrics().get(1).setRawValue(85);
        scenA.getDimensions().get(1).getMetrics().get(0).setRawValue(8);
        scenA.getDimensions().get(1).getMetrics().get(1).setRawValue(90);
        scenA.getDimensions().get(2).getMetrics().get(0).setRawValue(12);
        scenA.getDimensions().get(2).getMetrics().get(1).setRawValue(88);
        scenA.getDimensions().get(3).getMetrics().get(0).setRawValue(92);
        scenA.getDimensions().get(3).getMetrics().get(1).setRawValue(22);
        scenarios.put("A", scenA);

        // Scenario B
        Scenario scenB = new Scenario("B", "Scenario B — Code Review Cycle", "Health", "Process Quality");

        Dimension reviewEff = new Dimension("Review Efficiency", 35);
        reviewEff.addMetric(new Metric("Review turnaround",   50, false, 0,  72, "hrs"));
        reviewEff.addMetric(new Metric("First-pass approval", 50, true,  0, 100, "%"));
        scenB.addDimension(reviewEff);

        Dimension codeQuality2 = new Dimension("Code Quality", 35);
        codeQuality2.addMetric(new Metric("Bug escape rate",   50, false, 0,  20, "%"));
        codeQuality2.addMetric(new Metric("Refactor coverage", 50, true,  0, 100, "%"));
        scenB.addDimension(codeQuality2);

        Dimension teamCollab2 = new Dimension("Team Collaboration", 30);
        teamCollab2.addMetric(new Metric("Comment resolution rate", 50, true, 0, 100, "%"));
        teamCollab2.addMetric(new Metric("Cross-team review %",     50, true, 0, 100, "%"));
        scenB.addDimension(teamCollab2);

        scenB.getDimensions().get(0).getMetrics().get(0).setRawValue(18);
        scenB.getDimensions().get(0).getMetrics().get(1).setRawValue(78);
        scenB.getDimensions().get(1).getMetrics().get(0).setRawValue(7);
        scenB.getDimensions().get(1).getMetrics().get(1).setRawValue(82);
        scenB.getDimensions().get(2).getMetrics().get(0).setRawValue(91);
        scenB.getDimensions().get(2).getMetrics().get(1).setRawValue(74);
        scenarios.put("B", scenB);
    }

    public static List<Scenario> getScenarios(String mode, String qualityType) {
        List<Scenario> result = new ArrayList<>();
        for (Scenario sc : scenarios.values()) {
            if (sc.getMode().equalsIgnoreCase(mode) &&
                    sc.getQualityType().equalsIgnoreCase(qualityType)) {
                result.add(sc);
            }
        }
        return result;
    }

    public static Scenario getById(String id) {
        return scenarios.get(id);
    }
}