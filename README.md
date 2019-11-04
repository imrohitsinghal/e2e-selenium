# e2e Automation
A generic framework based on POM (Page Object Model) which takes care of Mweb, Web and Mobile Platforms(iOS and Android).

# Libraries used:
1. Appium
2. Selenium WebDriver
3. Java
4. Cucumber-java, Cucumber-junit
5. jUnit
6. Log4j
7. Gradle

# External dependencies
1. imagemagick
   * [Download](https://imagemagick.org/script/download.php)

# How this framework works:
This framework is built in BDD style using cucumber framework and scenarios will be run using cucumber-junit runners.<br/>

Scenario tagging: <br/>
 
 @web - for web tests <br/>
 @mweb - for Mweb tests <br/>
 @android - for Android tests <br/>
 @ios - for iOS tests <br/>
 @ignore - to ignore certain tests <br/>
 @visual - to include visual tests <br/>
 
 
# Visual Testing

    -   Tests can be executed in two modes 
        1. tulna.mode='build'
        2. tulna.mode='compare'
        
    - What's build or compare?
        1. If you need to capture fresh baselines, then run it in build mode
        2. If you want a comparison, then run it in compare mode
        
# Running tests for the first time:

* Running tag based features <br/>

  `gradle clean test -Pplatform=web -Pbrowser=chrome -Penv=qa -Ptulna.mode=compare -Ptulna.platform=web -Ptags="@home"` <br/>


* Execution on different platforms <br/>
        
    * FOR Web:
    
        `gradle clean test -Pplatform=web -Pbrowser=chrome -Penv=qa -Ptulna.mode=<build/compare> -Ptulna.platform=web -Ptags="@tag1,@tag2"` <br/>
     
    * FOR MWeb:
    
        `gradle clean test -Pplatform=mweb -Pbrowser=chrome -Penv=qa -Ptulna.mode=<build/compare> -Ptulna.platform=mweb -Ptags="@tag1,@tag2"` <br/>
                    
    * FOR Android:
    
        `gradle clean test -Pplatform=android -Ptulna.mode=build -Ptulna.platform=android -Ptulna.platform=mweb -Ptags="@tag1,@tag2"` <br/>
        
    * FOR iOS:
        
         `gradle clean test -Pplatform=iOS -Ptulna.mode=build -Ptulna.platform=iOS -Ptulna.platform=mweb -Ptags="@tag1,@tag2"` <br/>
     
# Reports and Results:

* Test Report Location - `/cucumber-report/index.html`

* Execution with visual mode as 'build': <br/>
    
    - All the baselines are stored in `Baselines` directory (`/web/baseline_images`)
    
* Execution with visual mode as 'compare' : <br/>
    -  In case a test fails, actual and difference images are generated in `Baselines` directory under sub-directory 'actual_images' (`/web/actual_images`)
    
# Masking:

* Want to mask some dynamic content during visual comparison? 
  
  - Add the co-ordinates for the image which you want to mask in `tulna.yaml` <br/>
    (Ex. `homepage: {from_date: [x1, x2, y1, y2]}`) 
  - Add locators for elements which needs masking in `tulna.yaml` <br/>
    (Ex. `homepage: ["css:div.highlight__decoration[style^=\"top\"]"]`) 
  - Tool you can use to find co-ordinates - [GIMP](https://www.gimp.org/)
    
    
#### Note: Want to run it in non-headless mode??

    - Comment `options.addArguments("headless");` in DriverManager class.

### Contributing

Fork the project, make a change, and send a pull request! 
Danke! 
