package com.comoressoft.mybudget.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.comoressoft.mybudget.dto.CategoryDTO;
import com.comoressoft.mybudget.dto.FamilyDTO;
import com.comoressoft.mybudget.dto.ItemDTO;
import com.comoressoft.mybudget.dto.ItemShoppingListDTO;
import com.comoressoft.mybudget.dto.ShoppingListDTO;
import com.comoressoft.mybudget.dto.SubCategoryDTO;
import com.comoressoft.mybudget.dto.UserDTO;
import com.comoressoft.mybudget.entity.Category;
import com.comoressoft.mybudget.entity.Family;
import com.comoressoft.mybudget.entity.Item;
import com.comoressoft.mybudget.entity.ItemShoppingList;
import com.comoressoft.mybudget.entity.ShoppingList;
import com.comoressoft.mybudget.entity.SubCategory;
import com.comoressoft.mybudget.entity.User;

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
			@Mapping(target = "catType", source = "categoryType"),
			@Mapping(target = "subCategories", source = "subCategory") })
	CategoryDTO categoryToCategoryDTO(Category category);

	/**
	 * 
	 * @param categoryDTO
	 * @return category
	 */
	@Mappings({ @Mapping(target = "id", source = "catId"), @Mapping(target = "categoryLabel", source = "catLabel"),
			@Mapping(target = "categoryState", source = "catState"),
			@Mapping(target = "categoryTotalCost", source = "catTotalCost"),
			@Mapping(target = "categoryType", source = "catType"),
			@Mapping(target = "subCategory", source = "subCategories") })
	Category categoryDTOToCategory(CategoryDTO categoryDTO);

	/**
	 * 
	 * @param subCategory
	 * @return subCategoryDTO
	 */
	@Mapping(target = "subCatId", source = "id")
	@Mapping(target = "subCatLabel", source = "subCategoryLabel")
	@Mapping(target = "subCatState", source = "subCategoryState")
	@Mapping(target = "subCatTotalCost", source = "subCategoryTotalCost")
	SubCategoryDTO subCategoryToSubCategoryDTO(SubCategory subCategory);

	/**
	 * 
	 * @param subCategoryDTO
	 * @return subCategory
	 */
	@Mappings({ @Mapping(target = "id", source = "subCatId"),
			@Mapping(target = "subCategoryLabel", source = "subCatLabel"),
			@Mapping(target = "subCategoryState", source = "subCatState"),
			@Mapping(target = "subCategoryTotalCost", source = "subCatTotalCost") })
	SubCategory subCategoryDTOToSubCategory(SubCategoryDTO subCategoryDTO);

	@Mapping(target = "itemId", source = "id")
	ItemDTO itemToItemDTO(Item item);

	@Mapping(target = "id", source = "itemId")
	Item itemDTOToItem(ItemDTO itemDTO);

	ShoppingListDTO shoppingListToShoppingListDTO(ShoppingList shoppingList);

	ShoppingList shoppingListDTOToShoppingList(ShoppingListDTO shoppingListDTO);

	ItemShoppingListDTO itemShoppingListToITemShoppingListDTO(ItemShoppingList itemShoppingList);

	ItemShoppingList itemShoppingListDTOToITemShoppingList(ItemShoppingListDTO itemShoppingListDTO);
	
	FamilyDTO familyToFamilyDTO(Family family);
	
	Family familyDTOToFamily(FamilyDTO family);
	
	User userDTOToUser(UserDTO userDto);
	UserDTO userToUserDTO(User user);

}
