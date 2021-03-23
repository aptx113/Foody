import com.android.build.gradle.internal.dsl.ProductFlavor
import org.gradle.api.NamedDomainObjectContainer

interface BuildProductFlavor {
    val name: String

    fun appCreate(namedDomainObjectContainer: NamedDomainObjectContainer<ProductFlavor>): ProductFlavor

    fun libraryCreate(namedDomainObjectContainer: NamedDomainObjectContainer<ProductFlavor>): ProductFlavor
}

object ProductFlavorDevelop : BuildProductFlavor {
    override val name: String = "dev"

    override fun appCreate(namedDomainObjectContainer: NamedDomainObjectContainer<ProductFlavor>): ProductFlavor =
        namedDomainObjectContainer.create(name) {
            applicationIdSuffix = ".dev"
            versionNameSuffix = "-dev"
            dimension = BuildProductDimensions.ENVIRONMENT
        }

    override fun libraryCreate(namedDomainObjectContainer: NamedDomainObjectContainer<ProductFlavor>): ProductFlavor =
        namedDomainObjectContainer.create(name) {
            versionNameSuffix = "-dev"
            dimension = BuildProductDimensions.ENVIRONMENT
        }
}

object ProductFlavorQA : BuildProductFlavor {
    override val name: String = "qa"

    override fun appCreate(namedDomainObjectContainer: NamedDomainObjectContainer<ProductFlavor>): ProductFlavor =
        namedDomainObjectContainer.create(name) {
            applicationIdSuffix = ".qa"
            versionNameSuffix = "-qa"
            dimension = BuildProductDimensions.ENVIRONMENT
        }

    override fun libraryCreate(namedDomainObjectContainer: NamedDomainObjectContainer<ProductFlavor>): ProductFlavor =
        namedDomainObjectContainer.create(name) {
            versionNameSuffix = "-qa"
            dimension = BuildProductDimensions.ENVIRONMENT
        }
}

object ProductFlavorProduction : BuildProductFlavor {
    override val name: String = "prod"

    override fun appCreate(namedDomainObjectContainer: NamedDomainObjectContainer<ProductFlavor>): ProductFlavor =
        namedDomainObjectContainer.create(name) {
            dimension = BuildProductDimensions.ENVIRONMENT
        }

    override fun libraryCreate(namedDomainObjectContainer: NamedDomainObjectContainer<ProductFlavor>): ProductFlavor =
        namedDomainObjectContainer.create(name) {
            dimension = BuildProductDimensions.ENVIRONMENT
        }
}
