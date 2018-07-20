package calle.david.udacityrecipeapp.Data.Database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Steps {
    @PrimaryKey(autoGenerate = true)
    private int stepPK;
    private int id;
    private int recipeID;
    private String shortDescription;
    private String description;
    private String videoURL;
    private String thumbnailURL;


    public Steps(int stepPK, int id, int recipeID, String shortDescription, String description, String videoURL, String thumbnailURL){
        this.stepPK = stepPK;
        this.id = id;
        this.recipeID = recipeID;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoURL = videoURL;
        this.thumbnailURL = thumbnailURL;
    }
    @Ignore
    public Steps() {

    }
    @Ignore
    public Steps(int recipeID) {
        this.recipeID = id;
    }
    @Ignore
    public Steps(int recipeID, int stepID, String shortDescription, String description, String thumbnailURL, String videoURL) {
        this.recipeID = recipeID;
        this.id = stepID;
        this.shortDescription = shortDescription;
        this.description = description;
        this.thumbnailURL = thumbnailURL;
        this.videoURL = videoURL;
    }

    public int getStepPK() {
        return stepPK;
    }

    public void setStepPK(int stepPK) {
        this.stepPK = stepPK;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    public int getRecipeID() {
        return recipeID;
    }

    public void setRecipeID(int recipeID) {
        this.recipeID = recipeID;
    }
}
