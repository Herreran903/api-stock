package com.api_stock.stock.infra.category.out;

import com.api_stock.stock.domain.category.util.CategoryConstants;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "category")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = CategoryConstants.MAX_NAME_LENGTH)
    private String name;

    @Column(nullable = false, length = CategoryConstants.MAX_DESCRIPTION_LENGTH)
    private String description;
}
