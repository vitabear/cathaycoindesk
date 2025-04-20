
package com.example.cathaycoindesk.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Currency {
    @Id
    @Column(length = 10)
    private String code;

    private String nameZh;
    private String description;
    private String symbol;
}
