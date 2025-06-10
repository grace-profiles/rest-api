import org.grails.cli.interactive.completers.DomainClassCompleter

description("Generates a Controller that performs CRUD operations and the associated views") {
  usage "grace generate-all [Domain Class]"
  argument name:'Domain Class', description:"The name of the Domain Class", required:true
  completer DomainClassCompleter
  synonyms 'generate-resource-controller'
  flag name:'force', description:"Whether to overwrite existing files"
}

if(args) {
    generateController(*args)
    generateViews(*args)
    generateFunctionalTest(*args)    
}
else {
    error "No domain class specified"
}
