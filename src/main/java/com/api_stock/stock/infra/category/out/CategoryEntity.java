package com.api_stock.stock.infra.category.out;

import jakarta.persistence.*;
import lombok.*;

import static com.api_stock.stock.domain.category.util.CategoryConstants.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = CATEGORY_TABLE_NAME)
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = MAX_NAME_LENGTH)
    private String name;

    @Column(nullable = false, length = MAX_DESCRIPTION_LENGTH)
    private String description;
}
