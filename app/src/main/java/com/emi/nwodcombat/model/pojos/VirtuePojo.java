package com.emi.nwodcombat.model.pojos;

public class VirtuePojo {

    private Long id;

    private String Name;
    private String RegainAll;
    private String Description;

    public VirtuePojo() {
    }

    public VirtuePojo(Long id) {
        this.id = id;
    }

    public VirtuePojo(Long id, String Name, String RegainAll, String Description) {
        this.id = id;
        this.Name = Name;
        this.RegainAll = RegainAll;
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

    public String getRegainAll() {
        return RegainAll;
    }

    public void setRegainAll(String RegainAll) {
        this.RegainAll = RegainAll;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }
}
