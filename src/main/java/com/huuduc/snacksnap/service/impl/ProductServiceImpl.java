package com.huuduc.snacksnap.service.impl;

import com.huuduc.snacksnap.data.dto.ProductDTORequest;
import com.huuduc.snacksnap.data.dto.ThongKe;
import com.huuduc.snacksnap.data.entity.*;
import com.huuduc.snacksnap.data.dto.ProductDTOResponse;
import com.huuduc.snacksnap.exception.NotFoundException;
import com.huuduc.snacksnap.repository.*;
import com.huuduc.snacksnap.service.FileService;
import com.huuduc.snacksnap.service.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private FileService fileService;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private CartDetailsRepositoy cartDetailsRepositoy;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private OrdersRepository ordersRepository;

    @Override
    public ProductDTOResponse create(Product product, MultipartFile file) {

        product.setFavorite(true);

        if(file!=null){
            String filename = this.fileService.uploadFile(file);

            if (filename == null) {
                throw new NotFoundException(Collections.singletonMap("null",null));
            }

            Image image = new Image();
            image.setName(filename);
            image.setProduct(product);

            product.setImg(image);
        }

        ProductDTOResponse productDTOResponse = new ProductDTOResponse();
        productDTOResponse.setName(product.getName());
        productDTOResponse.setPrice(product.getPrice());
        productDTOResponse.setFavorite(product.isFavorite());
        productDTOResponse.setImg(product.getImg().getName());
        productDTOResponse.setType(product.getType());

        this.productRepository.save(product);

        return productDTOResponse;

    }

    @Override
    public List<ProductDTOResponse> getAll() {

        List<Product> products = this.productRepository.findAll();

        for (int i=0;i<products.size();i++){
            if(products.get(i).getStatus()==0){
                products.remove(products.get(i));
            }
        }

        List<ProductDTOResponse> productDTOResponses = products
                .stream().map(p-> toDTO(p)).toList();
        return productDTOResponses;

    }

    @Override
    public List<ProductDTOResponse> getAllByFavorite() {

        List<Product> products = this.productRepository.findAll();
        List<Product> productListFavorite = new ArrayList<>();
        List<ProductDTOResponse> productDTOResponses = new ArrayList<>();

        for(Product product:products){

            if(product.isFavorite() && product.getStatus()==1){
                productListFavorite.add(product);
                productDTOResponses.add(toDTO(product));
            }
        }

        return productDTOResponses;
    }

    @Override
    public List<ProductDTOResponse> getAllByType(int type) {

        List<Product> products = this.productRepository.findByType(type);

        for (int i=0;i<products.size();i++){
            if(products.get(i).getStatus()==0){
                products.remove(products.get(i));
            }
        }

        List<ProductDTOResponse> productDTOResponses = products.stream().map(product -> toDTO(product)).toList();

        return productDTOResponses;

    }

    @Override
    public ProductDTOResponse create2(ProductDTORequest productDTORequest, File fileImages) {

        Product newProduct = toEntity(productDTORequest);

        //upload file to google drive
        if(fileImages!=null){

            String imgUrl = this.fileService.uploadFileToDrive(fileImages);

            if(imgUrl==null){
                throw new NotFoundException(Collections.singletonMap("File:",null));
            }

            Image newImage = new Image();
            newImage.setName(imgUrl);
            newImage.setProduct(newProduct);

            newProduct.setImg(newImage);
        }

        newProduct.setStatus(1);

        newProduct.setSold(0);

        this.productRepository.save(newProduct);

        return toDTO(newProduct);
    }

    @Override
    public ProductDTOResponse updateById(long productId, ProductDTORequest productDTORequest,File fileImage) {

        Product findProduct = this.productRepository.findById(productId).orElseThrow(
                ()-> new NotFoundException(Collections.singletonMap("Product id",productId))
        );

        if (!ObjectUtils.isEmpty(productDTORequest.getName())){
            findProduct.setName(productDTORequest.getName());
        }
        if(!ObjectUtils.isEmpty(productDTORequest.getPrice())){
            findProduct.setPrice(productDTORequest.getPrice());
        }
        if(!ObjectUtils.isEmpty(productDTORequest.getType())){
            findProduct.setType(productDTORequest.getType());
        }
        if(!ObjectUtils.isEmpty(productDTORequest.isFavorite())){
            findProduct.setFavorite(productDTORequest.isFavorite());
        }

        //upload file to google drive
        if(fileImage!=null){

            String imgUrl = this.fileService.uploadFileToDrive(fileImage);

            if(imgUrl==null){
                throw new NotFoundException(Collections.singletonMap("File:",null));
            }

            Image findImage = findProduct.getImg();

            findImage.setName(imgUrl);

            this.imageRepository.save(findImage);

        }

        List<CartDetails> cartDetailsList = this.cartDetailsRepositoy.findByProductIdAndOrdersIdIsNull(productId);
        for (CartDetails cartDetails:cartDetailsList){
            cartDetails.setProduct(findProduct);
            cartDetails.setPriceSP(findProduct.getPrice()*cartDetails.getSoLuong());

            cartDetailsRepositoy.save(cartDetails);
        }

        // Tính toán lại tổng giá của mỗi giỏ hàng
        for (CartDetails cartDetails : cartDetailsList) {
            Cart cart = cartDetails.getCart();
            List<CartDetails> cartDetailsInCart = cart.getCartDetailsList();
            double totalPrice = cartDetailsInCart.stream().mapToDouble(CartDetails::getPriceSP).sum();
            cart.setTotalPrice(totalPrice); // Cập nhật tổng giá
            cartRepository.save(cart); // Lưu thay đổi
        }

        this.productRepository.save(findProduct);

        return toDTO(findProduct);

    }

    @Transactional
    @Override
    public ProductDTOResponse delete(long productId) {

        Product findProduct = this.productRepository.findById(productId).orElseThrow(
                ()-> new NotFoundException(Collections.singletonMap("Product id",productId))
        );

        List<CartDetails> cartDetailsList = this.cartDetailsRepositoy.findByProductIdAndOrdersIdIsNull(productId);

        // Cập nhật giá của các giỏ hàng
        for (CartDetails cartDetails : cartDetailsList) {
            Cart cart = cartDetails.getCart();
            double totalPrice = cart.getTotalPrice() - (cartDetails.getPriceSP());
            cart.setTotalPrice(totalPrice);
            // Lưu thay đổi vào cơ sở dữ liệu
            cartRepository.save(cart);
        }

        cartDetailsRepositoy.deleteAll(cartDetailsList);

//        productRepository.delete(findProduct);

        findProduct.setStatus(0);
        productRepository.save(findProduct);

        return toDTO(findProduct);

    }

    @Override
    public List<ThongKe> thongKe() {

        List<Product> products = this.productRepository.findAll();
        List<ThongKe> thongKeList = new ArrayList<>();
        for (Product product: products){
            if(product.getSold()>0){
                ThongKe thongKe = new ThongKe();
                thongKe.setProductName(product.getName());
                thongKe.setQuantity(product.getSold());
                thongKeList.add(thongKe);
            }
        }

        return thongKeList;
    }

    public ProductDTOResponse toDTO(Product product){
        ProductDTOResponse productDTOResponse = new ProductDTOResponse();
        productDTOResponse.setId(product.getId());
        productDTOResponse.setName(product.getName());
        productDTOResponse.setPrice(product.getPrice());
        productDTOResponse.setFavorite(product.isFavorite());
        productDTOResponse.setImg(product.getImg().getName());
        productDTOResponse.setType(product.getType());

        return productDTOResponse;
    }

    public Product toEntity(ProductDTORequest productDTORequest){
        Product newProduct = new Product();
        newProduct.setName(productDTORequest.getName());
        newProduct.setPrice(productDTORequest.getPrice());
        newProduct.setType(productDTORequest.getType());
        newProduct.setFavorite(productDTORequest.isFavorite());

        return newProduct;
    }
}
