package com.jkxy.car.api.service.Impl;

import com.jkxy.car.api.dao.CarDao;
import com.jkxy.car.api.pojo.Car;
import com.jkxy.car.api.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("carService")
public class CarServiceImpl implements CarService {
    @Autowired
    private CarDao carDao;

    @Override
    public List<Car> findAll() {
        return carDao.findAll();
    }

    @Override
    public Car findById(int id) {
        return carDao.findById(id);
    }

    @Override
    public List<Car> findByCarName(String carName) {
        return carDao.findByCarName(carName);
    }

    @Override
    public void deleteById(int id) {
        carDao.deleteById(id);
    }

    @Override
    public void updateById(Car car) {
        carDao.updateById(car);
    }

    @Override
    public void insertCar(Car car) {
        carDao.insertCar(car);
    }

    @Override
    public String buyById(int id, int count) {
        int retryTimes = 2;
        do {
            Car car = carDao.findById(id);
            Long quantity = car.getQuantity();
            if (quantity < count) {
                return "数量不足，购买失败";
            }
            car.setQuantity(quantity - count);
            int row = carDao.updateQuantityById(car);
            if (row == 1) {
                return "购买成功";
            }
            retryTimes--;
        } while (retryTimes > 0);
        return "系统繁忙";
    }

    @Override
    public List<Car> fuzzyFindByCarName(String carName, Long start, Long end) {
        return carDao.fuzzyFindByCarName(carName, start, end);
    }
}
