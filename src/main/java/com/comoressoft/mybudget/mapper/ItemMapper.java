/**
 * 
 */
package com.comoressoft.mybudget.mapper;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.comoressoft.mybudget.dto.Item;
import com.comoressoft.mybudget.entity.ItemEntity;

/**
 * @author MHA14633
 *
 */
@Mapper(componentModel = "spring")
@DecoratedWith(ItemDecorator.class)
interface ItemMapper {

	@Mapping(target = "itemId", source = "id")
	Item itemToItemDTO(ItemEntity item);

	@Mapping(target = "id", source = "itemId")
	ItemEntity itemDTOToItem(Item itemDTO);
}
