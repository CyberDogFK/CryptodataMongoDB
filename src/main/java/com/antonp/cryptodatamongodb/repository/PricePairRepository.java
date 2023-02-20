package com.antonp.cryptodatamongodb.repository;

import com.antonp.cryptodatamongodb.model.Currency;
import com.antonp.cryptodatamongodb.model.PricePair;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PricePairRepository extends MongoRepository<PricePair, Long> {
    PricePair findTopByCurrency1LikeOrderByPriceDesc(Currency name);

    PricePair findTopByCurrency1LikeOrderByPriceAsc(Currency name);

    List<PricePair> findAllByCurrency1(Currency name, Pageable pageable);
}
