package com.usktea.plainold.builders;

import com.usktea.plainold.models.option.OptionData;
import com.usktea.plainold.models.category.CategoryId;
import com.usktea.plainold.models.common.Money;
import com.usktea.plainold.models.product.Description;
import com.usktea.plainold.models.product.Image;
import com.usktea.plainold.models.product.ProductId;
import com.usktea.plainold.models.product.ProductName;
import com.usktea.plainold.models.product.ProductStatus;
import com.usktea.plainold.models.product.Shipping;

import java.time.LocalDateTime;

public interface Builder {
    void setProductId(ProductId productId);

    void setPrice(Money price);

    void setProductName(ProductName productName);

    void setCategoryId(CategoryId categoryId);

    void setImage(Image image);

    void setDescription(Description description);

    void setStatus(ProductStatus productStatus);

    void setShipping(Shipping shipping);

    void setCreateAt(LocalDateTime createAt);

    void setUpdatedAt(LocalDateTime updatedAt);

    void setOptionData(OptionData optionData);
}
