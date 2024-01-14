package com.etiqa.specification;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Criteria {
    String key;
    Object value;
    SearchCriteria operation;
}
