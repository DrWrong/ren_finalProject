namespace java tk.drwrong.ontology.thrift


struct SenseorValues{
    1: double exH2s
    2: double exOxy2
    3: double exPH2
    4: double exTempl
    5: double exampleConducitivity2;
}

struct ReasonResult{
    1: bool reportH2S
    2: bool reportGQOW
    3: bool reportOil
    4: bool verifyData
}

service OntologyService{
    #获取个sensor的初始值
    SenseorValues getInitValues(),
    ReasonResult perfomReason(
        1: SenseorValues sensors,
    ),
}
