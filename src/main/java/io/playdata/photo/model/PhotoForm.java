package io.playdata.photo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhotoForm {

    private Long id;
    private String title;
    private String description;
    private String imageUrl;

}