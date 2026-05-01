package model;

import java.util.ArrayList;
import java.util.List;

public class Scenario {

    private String id;
    private String name;
    private String mode;
    private String qualityType;
    private List<Dimension> dimensions;

    public Scenario(String id, String name, String mode, String qualityType) {
        this.id = id;
        this.name = name;
        this.mode = mode;
        this.qualityType = qualityType;
        this.dimensions = new ArrayList<>();
    }

    public void addDimension(Dimension dimension) {
        dimensions.add(dimension);
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getMode() { return mode; }
    public String getQualityType() { return qualityType; }
    public List<Dimension> getDimensions() { return dimensions; }
}