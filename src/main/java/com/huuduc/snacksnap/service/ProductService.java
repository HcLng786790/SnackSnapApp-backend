package com.huuduc.snacksnap.service;

import com.huuduc.snacksnap.data.dto.ProductDTORequest;
import com.huuduc.snacksnap.data.dto.ThongKe;
import com.huuduc.snacksnap.data.entity.Product;
import com.huuduc.snacksnap.data.dto.ProductDTOResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Service
public interface ProductService {

    ProductDTOResponse create(Product product, MultipartFile file);

    List<ProductDTOResponse> getAll();

    List<ProductDTOResponse> getAllByFavorite();

    List<ProductDTOResponse> getAllByType(int type);

    ProductDTOResponse create2(ProductDTORequest productDTORequest, File fileImages);

    ProductDTOResponse updateById(long productId, ProductDTORequest productDTORequest,File fileImage);

    ProductDTOResponse delete(long productId);

    List<ThongKe> thongKe();
}
