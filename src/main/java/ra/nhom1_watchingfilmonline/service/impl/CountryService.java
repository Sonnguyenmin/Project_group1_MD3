package ra.nhom1_watchingfilmonline.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.nhom1_watchingfilmonline.model.entity.Countries;
import ra.nhom1_watchingfilmonline.repository.impl.CountryDao;

import java.util.List;
@Service
public class CountryService {
    @Autowired
    private CountryDao countryDao;

    public List<Countries> findAll() {
        return countryDao.findAll();
    }

    public Countries findById(Integer id) {
        return countryDao.findById(id);
    }

    public void save(Countries countries) {
//        Countries countries = Countries.builder()
//                .bannerId(bannerRequest.getBannerId())
//                .bannerName(bannerRequest.getBannerName())
//                .description(bannerRequest.getDescription())
//                .build();
        countryDao.save(countries);
    }

    public void delete(Integer id) {
        countryDao.delete(id);
    }
}
