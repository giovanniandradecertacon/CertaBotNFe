package br.com.certacon.certabotnfefiles.services;

import br.com.certacon.certabotnfefiles.helpers.SeleniumHelperComponent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginTabService {
    private final SeleniumHelperComponent helper;
}
