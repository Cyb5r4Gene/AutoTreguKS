package Models;

import java.util.ArrayList;
import java.util.List;

public class SearchConditions {

    int gjendja, markaID, modeliID,qyteti, seatsNr, doorsNr, climatissation, sellerType, owners, damaged=1;

    boolean metalike=true, mat=true;

    boolean sportPacket,  sportAmortization;

    RangeSearch cmimi, regjistrimiPare, km, fuqia;
    LicencePlates licencePlates = new LicencePlates();
    Fuel fuel = new Fuel();
    VehicleType vehicle = new VehicleType();
    TransmissionSearch transmission = new TransmissionSearch();
    ColorSearch colorSearch = new ColorSearch();
    InteriorSearch interiorSearch = new InteriorSearch();
    SecurityAndEnvironmentSearch security = new SecurityAndEnvironmentSearch();
    Airbag airbag = new Airbag();
    ParkingSearch parking = new ParkingSearch();
    String ownerID;
    List<String> postIdList;

    List<CarTypes> carTypes = new ArrayList<>();

    public SearchConditions(int gjendja, int markaID, int modeliID, int qyteti, int seatsNr, int doorsNr, int climatissation, int sellerType, int owners, int damaged, RangeSearch cmimi, RangeSearch regjistrimiPare, RangeSearch km, RangeSearch fuqia, LicencePlates licencePlates, Fuel fuel, VehicleType vehicle, TransmissionSearch transmission, ColorSearch colorSearch, InteriorSearch interiorSearch, SecurityAndEnvironmentSearch security, Airbag airbag, ParkingSearch parking) {
        this.gjendja = gjendja;
        this.markaID = markaID;
        this.modeliID = modeliID;
        this.qyteti = qyteti;
        this.seatsNr = seatsNr;
        this.doorsNr = doorsNr;
        this.climatissation = climatissation;
        this.sellerType = sellerType;
        this.owners = owners;
        this.damaged = damaged;
        this.cmimi = cmimi;
        this.regjistrimiPare = regjistrimiPare;
        this.km = km;
        this.fuqia = fuqia;
        this.licencePlates = licencePlates;
        this.fuel = fuel;
        this.vehicle = vehicle;
        this.transmission = transmission;
        this.colorSearch = colorSearch;
        this.interiorSearch = interiorSearch;
        this.security = security;
        this.airbag = airbag;
        this.parking = parking;
    }

    public List<CarTypes> getCarTypes() {
        return carTypes;
    }

    public void setCarTypes(List<CarTypes> carTypes) {
        this.carTypes = carTypes;
    }

    public List<String> getPostIdList() {
        return postIdList;
    }

    public void setPostIdList(List<String> postIdList) {
        this.postIdList = postIdList;
    }

    public String getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }

    public boolean isSportPacket() {
        return sportPacket;
    }

    public void setSportPacket(boolean sportPacket) {
        this.sportPacket = sportPacket;
    }

    public boolean isSportAmortization() {
        return sportAmortization;
    }

    public void setSportAmortization(boolean sportAmortization) {
        this.sportAmortization = sportAmortization;
    }

    public boolean isMetalike() {
        return metalike;
    }

    public void setMetalike(boolean metalike) {
        this.metalike = metalike;
    }

    public boolean isMat() {
        return mat;
    }

    public void setMat(boolean mat) {
        this.mat = mat;
    }

    public SearchConditions(){}

    public int getSeatsNr() {
        return seatsNr;
    }

    public void setSeatsNr(int seatsNr) {
        this.seatsNr = seatsNr;
    }

    public int getDoorsNr() {
        return doorsNr;
    }

    public void setDoorsNr(int doorsNr) {
        this.doorsNr = doorsNr;
    }

    public int getClimatissation() {
        return climatissation;
    }

    public void setClimatissation(int climatissation) {
        this.climatissation = climatissation;
    }

    public int getSellerType() {
        return sellerType;
    }

    public void setSellerType(int sellerType) {
        this.sellerType = sellerType;
    }

    public int getOwners() {
        return owners;
    }

    public void setOwners(int owners) {
        this.owners = owners;
    }

    public int getDamaged() {
        return damaged;
    }

    public void setDamaged(int damaged) {
        this.damaged = damaged;
    }

    public VehicleType getVehicle() {
        return vehicle;
    }

    public void setVehicle(VehicleType vehicle) {
        this.vehicle = vehicle;
    }

    public TransmissionSearch getTransmission() {
        return transmission;
    }

    public void setTransmission(TransmissionSearch transmission) {
        this.transmission = transmission;
    }

    public ColorSearch getColorSearch() {
        return colorSearch;
    }

    public void setColorSearch(ColorSearch colorSearch) {
        this.colorSearch = colorSearch;
    }

    public InteriorSearch getInteriorSearch() {
        return interiorSearch;
    }

    public void setInteriorSearch(InteriorSearch interiorSearch) {
        this.interiorSearch = interiorSearch;
    }

    public SecurityAndEnvironmentSearch getSecurity() {
        return security;
    }

    public void setSecurity(SecurityAndEnvironmentSearch security) {
        this.security = security;
    }

    public Airbag getAirbag() {
        return airbag;
    }

    public void setAirbag(Airbag airbag) {
        this.airbag = airbag;
    }

    public ParkingSearch getParking() {
        return parking;
    }

    public void setParking(ParkingSearch parking) {
        this.parking = parking;
    }

    public void setFuel(Fuel fuel) {
        this.fuel = fuel;
    }

    public LicencePlates getLicencePlates() {
        return licencePlates;
    }

    public void setLicencePlates(LicencePlates licencePlates) {
        this.licencePlates = licencePlates;
    }

    public int getMarkaID() {
        return markaID;
    }

    public void setMarkaID(int markaID) {
        this.markaID = markaID;
    }

    public int getModeliID() {
        return modeliID;
    }

    public void setModeliID(int modeliID) {
        this.modeliID = modeliID;
    }

    public Fuel getFuel() {
        return fuel;
    }

    public void setFuel(int karburantiID) {
        this.fuel = fuel;
    }

    public RangeSearch getCmimi() {
        return cmimi;
    }

    public void setCmimi(RangeSearch cmimi) {
        this.cmimi = cmimi;
    }

    public RangeSearch getRegjistrimiPare() {
        return regjistrimiPare;
    }

    public void setRegjistrimiPare(RangeSearch regjistrimiPare) {
        this.regjistrimiPare = regjistrimiPare;
    }

    public RangeSearch getKm() {
        return km;
    }

    public void setKm(RangeSearch km) {
        this.km = km;
    }

    public RangeSearch getFuqia() {
        return fuqia;
    }

    public void setFuqia(RangeSearch fuqia) {
        this.fuqia = fuqia;
    }

    public int getQyteti() {
        return qyteti;
    }

    public void setQyteti(int qyteti) {
        this.qyteti = qyteti;
    }

    public int getGjendja() {
        return gjendja;
    }

    public void setGjendja(int gjendja) {
        this.gjendja = gjendja;
    }


}
