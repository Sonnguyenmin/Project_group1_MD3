package ra.nhom1_watchingfilmonline.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.nhom1_watchingfilmonline.model.dto.request.BannerRequest;
import ra.nhom1_watchingfilmonline.model.entity.Banners;
import ra.nhom1_watchingfilmonline.repository.BannerDao;
import ra.nhom1_watchingfilmonline.service.UploadService;

import java.util.List;

@Service
public class BannerService {
    @Autowired
    private BannerDao bannerDao;
    @Autowired
    private UploadService uploadService;

    public List<Banners> findAll() {
        return bannerDao.findAll();
    }

    public Banners findById(Integer id) {
        return bannerDao.findById(id);
    }

    public void save(BannerRequest bannerRequest) {
        Banners banners = Banners.builder()
                .bannerId(bannerRequest.getBannerId())
                .bannerName(bannerRequest.getBannerName())
                .description(bannerRequest.getDescription())
                .build();

        if (bannerRequest.getBannerId() == null) {
            banners.setBannerImg(uploadService.uploadFileToServer(bannerRequest.getMultipartFile()));
        } else {
            if (bannerRequest.getMultipartFile() != null && bannerRequest.getMultipartFile().getSize() > 0) {
                banners.setBannerImg(uploadService.uploadFileToServer(bannerRequest.getMultipartFile()));
            } else {
                banners.setBannerImg(bannerDao.getImageById(bannerRequest.getBannerId()));
            }
        }
        bannerDao.save(banners);
    }

    public void delete(Integer id) {
        bannerDao.delete(id);
    }
}
