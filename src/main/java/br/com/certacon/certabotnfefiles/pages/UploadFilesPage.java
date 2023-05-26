package br.com.certacon.certabotnfefiles.pages;

import br.com.certacon.certabotnfefiles.helpers.SeleniumHelperComponent;
import br.com.certacon.certabotnfefiles.models.ProcessFileModel;
import br.com.certacon.certabotnfefiles.repositories.ProcessFileRepository;
import br.com.certacon.certabotnfefiles.utils.ProcessStatus;
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
                                                Optional<WebElement> tableContent = helper.findElementByXpathAnGetText(4000L, 30L, "/html/body/div[25]/div[1]/div/div/div/div/div[1]/div/div[1]/div/div/div/div/div/div[2]/div[1]/div/table", remoteWebDriver);
                                                if (tableContent.isPresent()) {
                                                    String[] tableData = tableContent.get().getText().split("\n");
                                                    for (int i = 0; i < tableData.length; i++) {
                                                        if (tableData[i].equals(searchCnpjVO.getCnpj() + "-" + searchCnpjVO.getNomeEmpresa().toUpperCase())) {
                                                            int td = i + 1;
                                                            Optional<WebElement> cnpjClickEspecificElement = helper.findElementNotButtonByXpathAndClick(1000L, 30L, "/html/body/div[25]/div[1]/div/div/div/div/div[1]/div/div[1]/div/div/div/div/div/div[2]/div[1]/div/table/tbody/tr[" + td + "]", remoteWebDriver);
                                                            if (cnpjClickEspecificElement.isPresent()) {
                                                                Optional<WebElement> confirmButtonElement = helper.findElementNotButtonByXpathAndClick(1000L, 30L, "/html/body/div[25]/div[1]/div/div/div/div/div[1]/div/div[2]/div/div[2]", remoteWebDriver);
                                                                if (confirmButtonElement.isPresent()) {
                                                                    Optional<WebElement> inputFilePathElement = helper.findElementByCssSelectorWithSendKeys(1000L, 30L, "input[type='file']", searchCnpjVO.getPathToFile(), remoteWebDriver);
                                                                    if (inputFilePathElement.isPresent()) {
                                                                        Optional<WebElement> modal;
                                                                        Thread.sleep(25000);
                                                                        do {
                                                                            Optional<WebElement> time = helper.findElementByXpathAnGetText(1000L, 30L, "/html/body/div[32]/div[1]/div/div/div/div/div/div/div[1]/div[2]", remoteWebDriver);
                                                                            Optional<WebElement> dataForTableElement = helper.findElementByXpathAnGetText(1000L, 30L, "/html/body/div[32]/div[1]/div/div/div/div/div/div/div[3]/div/div[3]/div/div[1]/div", remoteWebDriver);
                                                                            if (dataForTableElement.isPresent() && time.isPresent()) {
                                                                                String[] timeSplitted = time.get().getText().split("\n");
                                                                                String[] dataTable = dataForTableElement.get().getText().split(":");
                                                                                if (timeSplitted.length == 3) {
                                                                                    modelOptional.get().setTime(timeSplitted[1]);
                                                                                    processFileRepository.save(modelOptional.get());
                                                                                }
                                                                                if (dataTable.length == 3) {
                                                                                    String[] percentageAndQuantity = dataTable[2].split("\\(");
                                                                                    modelOptional.get().setPercentage(percentageAndQuantity[1].replaceAll("\\)", ""));
                                                                                    modelOptional.get().setRegisters(percentageAndQuantity[0]);
                                                                                    processFileRepository.save(modelOptional.get());
                                                                                }
                                                                            }
                                                                            modal = helper.findElementIsPresent(1000L, 10L, "/html/body/div[32]/div[1]", remoteWebDriver);
                                                                        } while (modal.isPresent());
                                                                        Optional<WebElement> errorProxy = helper.findElementIsPresent(1000L, 10L, "/html/body/div[33]", remoteWebDriver);
                                                                        if (errorProxy.isPresent()) {
                                                                            modelOptional.get().setStatus(ProcessStatus.PROXY_ERROR);
                                                                        }
                                                                        Optional<WebElement> validAndInvalidFiles = helper.findElementByXpathAnGetText(3000L, 200L, "/html/body/div[6]/div[2]/div/div/div/div/div/div[2]/div/div/div/div[1]/div/div[3]/div[2]/div/div/div/div/div[1]/div/div[2]/div/div[1]/div/div/div/div[2]/div[2]/div/table/tbody/tr/td[2]/div", remoteWebDriver);
                                                                        if (validAndInvalidFiles.isPresent()) {
                                                                            String[] validFilesAndInvalid = validAndInvalidFiles.get().getText().split(" ");
                                                                            String invalid = validFilesAndInvalid[5].replace(":", "");
                                                                            if (!invalid.equals("0")) {
                                                                                Optional<WebElement> report = helper.findElementByXpathAnGetText(2000L, 20L, "/html/body/div[6]/div[2]/div/div/div/div/div/div[2]/div/div/div/div[1]/div/div[3]/div[2]/div/div/div/div/div[1]/div/div[2]/div/div[1]/div/div/div/div[2]/div[2]/div/table/tbody/tr/td[2]/div/a", remoteWebDriver);
                                                                                String link = report.get().getAttribute("href");
                                                                                modelOptional.get().setReportLink(link);
                                                                            }
                                                                            modelOptional.get().setValid(validFilesAndInvalid[1].replace(":", ""));
                                                                            modelOptional.get().setInvalid(invalid);
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }

                                            }
                                        } else {
                                            Optional<WebElement> cnpjClickEspecificElement = helper.findElementNotButtonByXpathAndClick(1000L, 30L, "/html/body/div[25]/div[1]/div/div/div/div/div[1]/div/div[1]/div/div/div/div/div/div[2]/div[1]/div/table/tbody/tr", remoteWebDriver);
                                            if (cnpjClickEspecificElement.isPresent()) {
                                                Optional<WebElement> confirmButtonElement = helper.findElementNotButtonByXpathAndClick(1000L, 30L, "/html/body/div[25]/div[1]/div/div/div/div/div[1]/div/div[2]/div/div[2]/table/tbody/tr/td", remoteWebDriver);
                                                if (confirmButtonElement.isPresent()) {
                                                    Optional<WebElement> inputFilePathElement = helper.findElementByCssSelectorWithSendKeys(1000L, 30L, "input[type='file']", searchCnpjVO.getPathToFile(), remoteWebDriver);
                                                    if (inputFilePathElement.isPresent()) {
                                                        Optional<WebElement> modal;
                                                        Thread.sleep(25000);
                                                        do {
                                                            Optional<WebElement> time = helper.findElementByXpathAnGetText(1000L, 30L, "/html/body/div[32]/div[1]/div/div/div/div/div/div/div[1]/div[2]", remoteWebDriver);
                                                            Optional<WebElement> dataForTableElement = helper.findElementByXpathAnGetText(1000L, 30L, "/html/body/div[32]/div[1]/div/div/div/div/div/div/div[3]/div/div[3]/div/div[1]/div", remoteWebDriver);
                                                            if (dataForTableElement.isPresent() && time.isPresent()) {
                                                                String[] timeSplitted = time.get().getText().split("\n");
                                                                String[] dataTable = dataForTableElement.get().getText().split(":");
                                                                if (timeSplitted.length == 3) {
                                                                    modelOptional.get().setTime(timeSplitted[1]);
                                                                    processFileRepository.save(modelOptional.get());
                                                                }
                                                                if (dataTable.length == 3) {
                                                                    String[] percentageAndQuantity = dataTable[2].split("\\(");
                                                                    modelOptional.get().setPercentage(percentageAndQuantity[1].replaceAll("\\)", ""));
                                                                    modelOptional.get().setRegisters(percentageAndQuantity[0]);
                                                                    processFileRepository.save(modelOptional.get());
                                                                }
                                                            }
                                                            modal = helper.findElementIsPresent(1000L, 10L, "/html/body/div[32]/div[1]", remoteWebDriver);
                                                        } while (modal.isPresent());
                                                        Optional<WebElement> errorProxy = helper.findElementIsPresent(1000L, 10L, "/html/body/div[33]", remoteWebDriver);
                                                        if (errorProxy.isPresent()) {
                                                            modelOptional.get().setStatus(ProcessStatus.PROXY_ERROR);
                                                        }
                                                        Optional<WebElement> validAndInvalidFiles = helper.findElementByXpathAnGetText(3000L, 200L, "/html/body/div[6]/div[2]/div/div/div/div/div/div[2]/div/div/div/div[1]/div/div[3]/div[2]/div/div/div/div/div[1]/div/div[2]/div/div[1]/div/div/div/div[2]/div[2]/div/table/tbody/tr/td[2]/div", remoteWebDriver);
                                                        if (validAndInvalidFiles.isPresent()) {
                                                            String[] validFilesAndInvalid = validAndInvalidFiles.get().getText().split(" ");
                                                            String invalid = validFilesAndInvalid[5].replace(":", "");
                                                            if (!invalid.equals("0")) {
                                                                Optional<WebElement> report = helper.findElementByXpathAnGetText(2000L, 20L, "/html/body/div[6]/div[2]/div/div/div/div/div/div[2]/div/div/div/div[1]/div/div[3]/div[2]/div/div/div/div/div[1]/div/div[2]/div/div[1]/div/div/div/div[2]/div[2]/div/table/tbody/tr/td[2]/div/a", remoteWebDriver);
                                                                String link = report.get().getAttribute("href");
                                                                modelOptional.get().setReportLink(link);
                                                            }
                                                            modelOptional.get().setValid(validFilesAndInvalid[1].replace(":", ""));
                                                            modelOptional.get().setInvalid(invalid);
                                                            modelOptional.get().setStatus(ProcessStatus.OK);
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
