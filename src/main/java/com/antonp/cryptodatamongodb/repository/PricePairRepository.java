package com.antonp.cryptodatamongodb.repository;

import com.antonp.cryptodatamongodb.model.Currency;
import com.antonp.cryptodatamongodb.model.PricePair;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PricePairRepository extends MongoRepository<PricePair, Long> {

    // minimal
    // db.pricePair.find().sort({price: 1}).limit(1);

    // max
    // db.pricePair.find().sort({price: -1}).limit(1);


    //// Find MAX Value
    //
    //Order findTopByOrderByOrderDateDesc();
    //
    //// Find MIN Value
    //
    //Order findTopByOrderByOrderDateAsc();

    // Max
    PricePair findTopByCurrency1LikeOrderByPriceDesc(Currency name);

    // Min
    PricePair findTopByCurrency1LikeOrderByPriceAsc(Currency name);

    List<PricePair> findAllByCurrency1(Currency name, Pageable pageable);
}
