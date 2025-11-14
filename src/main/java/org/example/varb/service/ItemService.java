package org.example.varb.service;


import org.example.varb.entity.Category;
import org.example.varb.entity.Item;
import org.example.varb.repository.CategoryRepository;
import org.example.varb.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;

    public ItemService(ItemRepository itemRepository, CategoryRepository categoryRepository) {
        this.itemRepository = itemRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<Item> list() {
        return itemRepository.findAll();
    }

    public List<Item> listByCategory(Long categoryId) {
        return itemRepository.findByCategoryId(categoryId);
    }

    public Optional<Item> get(Long id) {
        return itemRepository.findById(id);
    }

    public Item create(Item item) {
        if (item.getCategory() != null && item.getCategory().getId() != null) {
            Category category = categoryRepository.findById(item.getCategory().getId()).orElseThrow();
            item.setCategory(category);
        }
        item.setUpdatedAt(LocalDateTime.now());
        return itemRepository.save(item);
    }

    public Item update(Long id, Item updatedItem) {
        return itemRepository.findById(id)
                .map(item -> {
                    item.setName(updatedItem.getName());
                    item.setSku(updatedItem.getSku());
                    item.setPrice(updatedItem.getPrice());
                    item.setStock(updatedItem.getStock());
                    item.setUpdatedAt(LocalDateTime.now());
                    item.setCategory(updatedItem.getCategory());
                    return itemRepository.save(item);
                })
                .orElse(null);
    }

    public boolean delete(Long id) {
        if (itemRepository.existsById(id)) {
            itemRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
