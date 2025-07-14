package ir.maktab.homeservice.controller;

import ir.maktab.homeservice.dto.AddressResponse;
import ir.maktab.homeservice.dto.AddressSaveRequest;
import ir.maktab.homeservice.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/addresses")
public class AddressController {

    private final AddressService addressService;


    @PostMapping
    public ResponseEntity<AddressResponse> submitAddress(
            @RequestBody AddressSaveRequest request) {
        return ResponseEntity.ok(addressService.submitAddress(request));
    }
}
