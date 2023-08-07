package com.Auton.gibg.controller.categoryController;

import com.Auton.gibg.entity.CategoryEntity.ProductCategory;
import com.Auton.gibg.service.CategoryService.ProductServiceCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cat")
public class ControllerCategory {


    @Autowired
    private ProductServiceCategory productServiceCategory;

    @GetMapping(value = "/Cat_repository")
    public ResponseEntity<List<ProductCategory>> findAll(){
        List<ProductCategory> list = productServiceCategory.findAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody ProductCategory productCategory) {
        // เช็คค่าและความถูกต้องของข้อมูลสินค้าหมวดหมู่
        if (productCategory.getCategory_name() == null || productCategory.getParentId() == null) {
            return ResponseEntity.badRequest().body("ชื่อสินค้าหมวดหมู่และ ID หมวดหมู่หลักต้องไม่เป็นค่าว่าง");
        }

        // เช็คว่า ID หมวดหมู่หลักที่ส่งมาถูกต้องหรือไม่
        if (productCategory.getParentId() < 0) {
            return ResponseEntity.badRequest().body("ID หมวดหมู่หลักต้องมีค่ามากกว่าหรือเท่ากับศูนย์");
        }

        // สร้างสินค้าหมวดหมู่ใหม่
        ProductCategory createdProductCategory = productServiceCategory.create(productCategory);

        // ส่งตอบกลับว่าสร้างสินค้าหมวดหมู่สำเร็จพร้อมข้อมูลสินค้าหมวดหมู่ที่สร้าง
        return ResponseEntity.ok(createdProductCategory);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable("id") Long id, @RequestBody ProductCategory productCategory) {
        ProductCategory existingCategory = productServiceCategory.findById(id);
        if (existingCategory == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ไม่พบข้อมูลสินค้าหมวดหมู่ที่คุณต้องการ");
//            return ResponseEntity.notFound().build();
        }
        // ดำเนินการอัปเดตข้อมูลสินค้าหมวดหมู่
    //    existingCategory.getCategory_name(productCategory.getParentId());
        existingCategory.setParentId(productCategory.getParentId());
        // สามารถอัปเดตฟิลด์อื่น ๆ ของ existingCategory ตามต้องการได้

        productServiceCategory.create(existingCategory);
        return ResponseEntity.ok("อัปเดตข้อมูลสินค้าหมวดหมู่เรียบร้อยแล้ว");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        try {

            if (productServiceCategory.findById(id)==null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ID not found");
            }else {
                productServiceCategory.delete(id);
            return ResponseEntity.ok("delete succes");
            }
//           productServiceCategory.delete(id);
//            return ResponseEntity.ok("delete succes");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while deleting data");
        }
    }
    @GetMapping("/findById/{id}")
    public ProductCategory getProductById(@PathVariable Long id) {

        return productServiceCategory.findById(id);
    }
    @GetMapping("/parentId/{id}")
    public List<ProductCategory> getProductByParentId(@PathVariable Long id) {
        return productServiceCategory.findByParentId(id);
    }



}
