package com.company.inventory.response;

import java.util.List;

import com.company.inventory.model.Category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponse {

	private List<Category>category;
}
