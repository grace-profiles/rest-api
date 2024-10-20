import org.grails.cli.interactive.completers.DomainClassCompleter

description( "Generates a controller that performs REST operations" ) {
  usage "grace generate-all [DOMAIN CLASS]"
  argument name:'Domain Class', description:"The name of the Domain Class", required:true
  completer DomainClassCompleter
  synonyms 'generate-resource-controller'
  flag name:'force', description:"Whether to overwrite existing files"
}

if(args) {
    generateController(*args)
    generateViews(*args)
    generateUnitTest(*args)
    generateFunctionalTest(*args)    
}
else {
    error "No domain class specified"
}
