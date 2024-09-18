package com.api_stock.stock.infra.brand.out;

import jakarta.persistence.*;
import lombok.*;

import static com.api_stock.stock.domain.brand.util.BrandConstants.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = BRAND_TABLE_NAME)
public class BrandEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = MAX_NAME_LENGTH)
    private String name;

    @Column(nullable = false, length = MAX_DESCRIPTION_LENGTH)
    private String description;

}
