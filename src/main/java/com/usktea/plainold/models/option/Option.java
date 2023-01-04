package com.usktea.plainold.models.option;

import com.usktea.plainold.models.product.ProductId;

import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
public class Option {
    @Id
    @GeneratedValue
    private Long id;

    @Embedded
    private ProductId productId;

    @ElementCollection
    private Set<String> sizes = new HashSet<>();

    @ElementCollection
    private Set<Color> colors = new HashSet<>();

    public Option() {
    }

    public Option(ProductId productId) {
        this.productId = productId;
    }

    public Option(Long id) {
        this.id = id;
    }

    public static Option fake(ProductId productId) {
        Option option = new Option(productId);

        option.addOptions();

        return option;
    }

    public void addOptions() {
        colors.add(new Color("Black", new Rgb(0), new Rgb(0), new Rgb(0)));
        colors.add(new Color("Gray", new Rgb(120), new Rgb(120), new Rgb(120)));
        colors.add(new Color("White", new Rgb(255), new Rgb(255), new Rgb(255)));

        sizes = Stream.of(Size.values())
                .map(Size::name)
                .collect(Collectors.toSet());
    }

    public Set<String> getSizes() {
        return sizes;
    }

    public Set<Color> getColors() {
        return colors;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        Option otherOption = (Option) object;

        return Objects.equals(id, otherOption.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
