package com.anhvt.shopapp.repositories;

import com.anhvt.shopapp.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // ke thua JpaRepository nen se ke thua mac dinh, bo di khong can
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
