package io.github.minothor.GrittyBusiness.constructs;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;

import java.util.Objects;

public class Sluice {

    private Location start, end;
    private BlockFace direction;
    private Sluice parent, child;

    public Sluice(){
        this(null);
    }

    public Sluice(Location start){
        this(start, null);

    }

    public Sluice(Location start, Sluice parent){
        this.setStart(start);
        this.parent = parent;
    }

    public Location getStart() {
        return start;
    }

    public void setStart(Location start) {
        this.start = start;
    }

    public Location getEnd() {
        return end;
    }

    public void setEnd(Location end) {
        this.end = end;
        // Check East/West
        if (this.end.getBlockX() != this.start.getBlockX()) {
            if (this.end.getBlockX() < this.start.getBlockX()){
                this.direction = BlockFace.EAST;
            } else {
                this.direction = BlockFace.WEST;
            }
        // Check North/South
        } else if (this.end.getBlockZ() != this.start.getBlockZ()) {
            if (this.end.getBlockZ() < this.start.getBlockZ()){
                this.direction = BlockFace.NORTH;
            } else {
                this.direction = BlockFace.SOUTH;
            }
        }
    }

    public boolean isPart(Location location){
        boolean result = false;
        //Check according to pattern,
        if(!result && Objects.nonNull(this.child))
            // Child check might not be needed, depending on how I pre-store sluices
            result = child.isPart(location);
        return result;
    }

}
