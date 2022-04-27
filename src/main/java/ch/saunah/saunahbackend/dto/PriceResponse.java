package ch.saunah.saunahbackend.dto;

import ch.saunah.saunahbackend.model.Price;
import ch.saunah.saunahbackend.model.Sauna;

public class PriceResponse {
    private int id;
    private float transportService;
    private float washService;
    private float saunahImp;
    private float deposit;
    private float handTowel;
    private float wood;


    public PriceResponse(Price price) {
        this.id = price.getId();
        this.transportService = price.getTransportService();
        this.washService = price.getWashService();
        this.saunahImp = price.getSaunahImp();
        this.deposit = price.getDeposit();
        this.handTowel = price.getHandTowel();
        this.wood = price.getWood();
     }

    public int getId() {
        return id;
    }


}
