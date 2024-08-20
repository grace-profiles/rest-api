import org.grails.cli.interactive.completers.DomainClassCompleter

description( "Generates a Controller that performs REST operations" ) {
  usage "grace generate-controller [DOMAIN CLASS]"
  argument name:'Domain Class', description:"The name of the Domain Class", required:true
  completer DomainClassCompleter
  synonyms 'generate-resource-controller'
  flag name:'force', description:"Whether to overwrite existing files"
}

if(args) {
  def classNames = args
  if(args[0] == '*') {
    classNames = resources("file:app/domain/**/*.groovy").collect { className(it) }
  }
  for(arg in classNames) {
    def sourceClass = source(arg)
    boolean overwrite = flag('force')
    if(sourceClass) {
      def model = model(sourceClass)
      render template: template('artifacts/scaffolding/Controller.groovy'),
             destination: file("app/controllers/${model.packagePath}/${model.convention('Controller')}.groovy"),
             model: model,
             overwrite: overwrite

      render template: template('artifacts/scaffolding/Service.groovy'),
              destination: file("app/services/${model.packagePath}/${model.convention('Service')}.groovy"),
              model: model,
              overwrite: overwrite

      render template: template('artifacts/scaffolding/Spec.groovy'),
              destination: file("src/test/groovy/${model.packagePath}/${model.convention('ControllerSpec')}.groovy"),
              model: model,
              overwrite: overwrite

      render template: template('artifacts/scaffolding/ServiceSpec.groovy'),
              destination: file("src/integration-test/groovy/${model.packagePath}/${model.convention('ServiceSpec')}.groovy"),
              model: model,
              overwrite: overwrite

      addStatus "Scaffolding completed for ${projectPath(sourceClass)}"
    }
    else {
      error "Domain class not found for name $arg"
    }
  }
}
else {
    error "No domain class specified"
}
