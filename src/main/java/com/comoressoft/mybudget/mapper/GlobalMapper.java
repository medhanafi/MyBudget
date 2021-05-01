package com.comoressoft.mybudget.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.comoressoft.mybudget.dto.Category;
import com.comoressoft.mybudget.dto.Family;
import com.comoressoft.mybudget.dto.Item;
import com.comoressoft.mybudget.dto.ItemShoppingList;
import com.comoressoft.mybudget.dto.ShoppingList;
import com.comoressoft.mybudget.dto.SubCategory;
import com.comoressoft.mybudget.entity.CategoryEntity;
import com.comoressoft.mybudget.entity.FamilyEntity;
import com.comoressoft.mybudget.entity.ItemEntity;
import com.comoressoft.mybudget.entity.ItemShoppingListEntity;
import com.comoressoft.mybudget.entity.ShoppingListEntity;
import com.comoressoft.mybudget.entity.SubCategoryEntity;

@Mapper(componentModel = "spring")
public interface GlobalMapper {
	/**
	 * 
	 * @param category
	 * @return categoryDTO
	 */
	@Mappings({ @Mapping(target = "catId", source = "id"), @Mapping(target = "catLabel", source = "categoryLabel"),
			@Mapping(target = "catState", source = "categoryState"),
			@Mapping(target = "catTotalCost", source = "categoryTotalCost"),
			@Mapping(target = "subCategories", source = "subCategory") })
	Category categoryToCategoryDTO(CategoryEntity category);

	/**
	 * 
	 * @param categoryDTO
	 * @return category
	 */
	@Mappings({ @Mapping(target = "id", source = "catId"), @Mapping(target = "categoryLabel", source = "catLabel"),
			@Mapping(target = "categoryState", source = "catState"),
			@Mapping(target = "categoryTotalCost", source = "catTotalCost"),
			@Mapping(target = "subCategory", source = "subCategories") })
	CategoryEntity categoryDTOToCategory(Category categoryDTO);

	/**
	 * 
	 * @param subCategory
	 * @return subCategoryDTO
	 */
	@Mapping(target = "subCatId", source = "id")
	@Mapping(target = "subCatLabel", source = "subCategoryLabel")
	@Mapping(target = "subCatState", source = "subCategoryState")
	@Mapping(target = "subCatTotalCost", source = "subCategoryTotalCost")
	SubCategory subCategoryToSubCategoryDTO(SubCategoryEntity subCategory);

	/**
	 * 
	 * @param subCategoryDTO
	 * @return subCategory
	 */
	@Mappings({ @Mapping(target = "id", source = "subCatId"),
			@Mapping(target = "subCategoryLabel", source = "subCatLabel"),
			@Mapping(target = "subCategoryState", source = "subCatState"),
			@Mapping(target = "subCategoryTotalCost", source = "subCatTotalCost") })
	SubCategoryEntity subCategoryDTOToSubCategory(SubCategory subCategoryDTO);

	@Mapping(target = "itemId", source = "id")
	Item itemToItemDTO(ItemEntity item);

	@Mapping(target = "id", source = "itemId")
	ItemEntity itemDTOToItem(Item itemDTO);

	ShoppingList shoppingListToShoppingListDTO(ShoppingListEntity shoppingList);

	ShoppingListEntity shoppingListDTOToShoppingList(ShoppingList shoppingListDTO);

	ItemShoppingList itemShoppingListToITemShoppingListDTO(ItemShoppingListEntity itemShoppingList);

	ItemShoppingListEntity itemShoppingListDTOToITemShoppingList(ItemShoppingList itemShoppingListDTO);
	
	Family familyToFamilyDTO(FamilyEntity family);
	
	FamilyEntity familyDTOToFamily(Family family);
	

}
