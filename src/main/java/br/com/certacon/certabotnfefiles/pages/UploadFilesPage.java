package br.com.certacon.certabotnfefiles.pages;

import br.com.certacon.certabotnfefiles.helpers.SeleniumHelperComponent;
import br.com.certacon.certabotnfefiles.models.ProcessFileModel;
import br.com.certacon.certabotnfefiles.repositories.ProcessFileRepository;
import br.com.certacon.certabotnfefiles.vos.ProcessFileForSearchVO;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class UploadFilesPage {
    private final SeleniumHelperComponent helper;
    private final ProcessFileRepository processFileRepository;

    public UploadFilesPage(SeleniumHelperComponent helper, ProcessFileRepository processFileRepository) {
        this.helper = helper;
        this.processFileRepository = processFileRepository;
    }

    public ProcessFileModel navigateOnUploadPage(ProcessFileForSearchVO searchCnpjVO, RemoteWebDriver remoteWebDriver) throws InterruptedException {
        Optional<ProcessFileModel> modelOptional = processFileRepository.findById(searchCnpjVO.toModel().getId());
        String[] cnpjTratada = searchCnpjVO.getCnpj().split("/");
        if (modelOptional.isPresent()) {
            Optional<WebElement> inputSearchTabElement = helper.findElementByXpathAndSendKeysString(1000L, 30L, "/html/body/div[6]/div[2]/div/div/div/div/div/div[2]/div/div/div/div[1]/div/div[3]/div[2]/div/div/div/div/div[1]/div/div[2]/div/div[2]/div[1]/div/div[1]/div/div/div/form/table/tbody/tr[2]/td[2]/table/tbody/tr/td[1]/input", "TRIBUTARIO", remoteWebDriver);

            if (inputSearchTabElement.isPresent()) {
                Optional<WebElement> clickSearchElement = helper.findElementNotButtonByXpathAndClick(1000L, 30L, "/html/body/div[6]/div[2]/div/div/div/div/div/div[2]/div/div/div/div[1]/div/div[3]/div[2]/div/div/div/div/div[1]/div/div[2]/div/div[2]/div[1]/div/div[1]/div/div/div/form/table/tbody/tr[2]/td[2]/table/tbody/tr/td[2]/table/tbody/tr/td[1]/span", remoteWebDriver);

                if (clickSearchElement.isPresent()) {
                    Optional<WebElement> cnpjForNfeElement = helper.findElementByXpathAndDoubleClick(1000L, 30L, "/html/body/div[6]/div[2]/div/div/div/div/div/div[2]/div/div/div/div[1]/div/div[3]/div[2]/div/div/div/div/div[1]/div/div[2]/div/div[2]/div[1]/div/div[3]/div[2]/div/table/tbody/tr[1]", remoteWebDriver);

                    if (cnpjForNfeElement.isPresent()) {
                        Optional<WebElement> nfeElement = helper.findElementByXpathAndDoubleClick(1000L, 30L, "/html/body/div[6]/div[2]/div/div/div/div/div/div[2]/div/div/div/div[1]/div/div[3]/div[2]/div/div/div/div/div[1]/div/div[2]/div/div[2]/div[1]/div/div[3]/div[2]/div/table/tbody/tr[15]", remoteWebDriver);

                        if (nfeElement.isPresent()) {
                            Optional<WebElement> uploadElement = helper.findElementNotButtonByXpathAndClick(1000L, 30L, "/html/body/div[6]/div[2]/div/div/div/div/div/div[2]/div/div/div/div[1]/div/div[3]/div[2]/div/div/div/div/div[1]/div/div[2]/div/div[1]/div/div/div/div[1]/div/div[3]/div/div/table/tbody/tr", remoteWebDriver);

                            if (uploadElement.isPresent()) {
                                Optional<WebElement> empresasInputElement = helper.findElementByXpathAndSendKeysString(1000L, 30L, "/html/body/div[25]/div[1]/div/div/div/div/div[1]/div/div[1]/div/div/div/div/div/div[1]/div/div/div/form/table/tbody/tr[2]/td[2]/table/tbody/tr/td[1]/input", cnpjTratada[0], remoteWebDriver);

                                if (empresasInputElement.isPresent()) {
                                    Optional<WebElement> empresasSearchButtonElement = helper.findElementNotButtonByXpathAndClick(1000L, 30L, "/html/body/div[25]/div[1]/div/div/div/div/div[1]/div/div[1]/div/div/div/div/div/div[1]/div/div/div/form/table/tbody/tr[2]/td[2]/table/tbody/tr/td[2]/table/tbody/tr/td[1]/span", remoteWebDriver);

                                    if (empresasSearchButtonElement.isPresent()) {
                                        String cssValue = helper.findElementByXpathAndGetCssValue(1000L, 30L, "/html/body/div[25]/div[1]/div/div/div/div/div[1]/div/div[1]/div/div/div/div/div/div[2]/div[1]/div/table/tbody/tr/td/div/table/tbody/tr/td[2]/nobr/span[1]", "background-image", remoteWebDriver);
                                        String url = "url(\"http://192.168.0.62/CertaconWeb/certacon/sc/skins/Graphite/images/TreeGrid/connector_closed_single.gif\")";

                                        Optional<WebElement> empresaCnpjElement = helper.findElementByXpathAndDoubleClick(1000L, 30L, "/html/body/div[25]/div[1]/div/div/div/div/div[1]/div/div[1]/div/div/div/div/div/div[2]/div[1]/div/table/tbody/tr/td/div/table/tbody/tr/td[2]/nobr/span[1]", remoteWebDriver);

                                        if (cssValue.equals(url)) {
                                            if (empresaCnpjElement.isPresent()) {
                                                String[] tableContent = helper.findElementByXpathAnGetText(1000L, 30L, "/html/body/div[25]/div[1]/div/div/div/div/div[1]/div/div[1]/div/div/div/div/div/div[2]/div[1]/div/table", remoteWebDriver).split("\n");
                                                for (int i = 0; i < tableContent.length; i++) {
                                                    if (tableContent[i].equals(searchCnpjVO.getCnpj() + "-" + searchCnpjVO.getNomeEmpresa().toUpperCase())) {
                                                        int td = i + 1;
                                                        Optional<WebElement> cnpjClickEspecificElement = helper.findElementNotButtonByXpathAndClick(1000L, 30L, "/html/body/div[25]/div[1]/div/div/div/div/div[1]/div/div[1]/div/div/div/div/div/div[2]/div[1]/div/table/tbody/tr[" + td + "]", remoteWebDriver);
                                                        if (cnpjClickEspecificElement.isPresent()) {
                                                            Optional<WebElement> confirmButtonElement = helper.findElementNotButtonByXpathAndClick(1000L, 30L, "/html/body/div[25]/div[1]/div/div/div/div/div[1]/div/div[2]/div/div[2]/table/tbody/tr", remoteWebDriver);
                                                            if (confirmButtonElement.isPresent()) {
                                                                //Optional<WebElement> inputFilePathElement = helper.findElementByCssSelectorWithSendKeys(1000L, 30L, "input[type='file']", ,remoteWebDriver);
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }

                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return modelOptional.get();
    }
}
