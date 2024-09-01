package com.api_stock.stock.infra.brand.out;

import com.api_stock.stock.domain.brand.util.BrandConstants;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "brand")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BrandEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = BrandConstants.MAX_NAME_LENGTH)
    private String name;

    @Column(nullable = false, length = BrandConstants.MAX_DESCRIPTION_LENGTH)
    private String description;

}
