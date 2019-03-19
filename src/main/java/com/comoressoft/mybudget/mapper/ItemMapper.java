/**
 * 
 */
package com.comoressoft.mybudget.mapper;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.comoressoft.mybudget.dto.ItemDTO;
import com.comoressoft.mybudget.entity.Item;

/**
 * @author MHA14633
 *
 */
@Mapper(componentModel = "spring")
@DecoratedWith(ItemDecorator.class)
interface ItemMapper {

	@Mapping(target = "itemId", source = "id")
	ItemDTO itemToItemDTO(Item item);

	@Mapping(target = "id", source = "itemId")
	Item itemDTOToItem(ItemDTO itemDTO);
}
