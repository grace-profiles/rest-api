import org.grails.cli.interactive.completers.DomainClassCompleter

description( "Generates a controller that performs REST operations" ) {
  usage "grace generate-views [DOMAIN CLASS]"
  argument name:'Domain Class', description:"The name of the domain class", required:true
  completer DomainClassCompleter
  
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

      render template: template('artifacts/scaffolding/index.gson'),
             destination: file("app/views/${model.propertyName}/index.gson"),
             model: model,
             overwrite: overwrite             

      render template: template('artifacts/scaffolding/show.gson'),
             destination: file("app/views/${model.propertyName}/show.gson"),
             model: model,
             overwrite: overwrite              

      render template: template("artifacts/scaffolding/_domain.gson"),
             destination: file("app/views/${model.propertyName}/_${model.propertyName}.gson"),
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
