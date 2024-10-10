package com.tujuhsembilan.app.repository;

import com.tujuhsembilan.app.model.Client;
import com.tujuhsembilan.app.model.Talent;
import com.tujuhsembilan.app.model.TalentWishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TalentWishlistRepository extends JpaRepository<TalentWishlist, Integer> {
    List<TalentWishlist> findAllByClient(Client client);

    List<TalentWishlist> findAllByTalent(Talent talent);

  //  List<TalentWishlist> din
    Long countAllByTalentWishlistId(TalentWishlist talentWishlist);
}
