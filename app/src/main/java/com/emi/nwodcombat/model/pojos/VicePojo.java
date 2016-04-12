package com.emi.nwodcombat.model.pojos;

public class VicePojo {

    private Long id;

    private String Name;
    private String RegainOne;
    private String Description;

    public VicePojo() {
    }

    public VicePojo(Long id) {
        this.id = id;
    }

    public VicePojo(Long id, String Name, String RegainOne, String Description) {
        this.id = id;
        this.Name = Name;
        this.RegainOne = RegainOne;
        this.Description = Description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getRegainOne() {
        return RegainOne;
    }

    public void setRegainOne(String RegainOne) {
        this.RegainOne = RegainOne;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }
}
