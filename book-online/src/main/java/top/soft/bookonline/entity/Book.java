package top.soft.bookonline.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author lhy
 * @description: TODO
 * @date 2024/10/20 16:24
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Book {
    private Integer id;
    private String name;
    private String cover;
    private String author;
    private Double price;
    private String detail;
}
