/**
 * 
 */
package com.comoressoft.mybudget.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.comoressoft.mybudget.dto.ItemDTO;
import com.comoressoft.mybudget.entity.Item;
import com.comoressoft.mybudget.repository.SubCategoryRepository;

/**
 * @author MHA14633
 *
 */
@Component
public abstract class ItemDecorator implements ItemMapper {

	@Autowired
	@Qualifier("delegate")
	private ItemMapper delegate;

	@Autowired
	private SubCategoryRepository subCategoryRepository;

	public ItemDTO itemToItemDTO(Item item) {
		ItemDTO itemDTO = delegate.itemToItemDTO(item);

		item.getSubCategory()
				.setSubCategoryTotalCost(item.getSubCategory().getSubCategoryTotalCost().add(item.getExpectedAmount()));

		subCategoryRepository.save(item.getSubCategory());

		return itemDTO;
	}

	public Item itemDTOToItem(ItemDTO itemDTO) {
		return delegate.itemDTOToItem(itemDTO);
	}

}
