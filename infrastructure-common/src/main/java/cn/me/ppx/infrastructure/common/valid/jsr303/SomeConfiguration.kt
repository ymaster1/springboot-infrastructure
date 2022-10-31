package cn.me.ppx.infrastructure.common.valid.jsr303



//@Configuration(proxyBeanMethods = false)
//@AutoConfigureBefore(ValidationAutoConfiguration::class)
//open class SomeConfiguration {
//    /**
//     * This bean definition is part of the workaround for a bug in hibernate-validation.
//     *
//     * It replaces the default validator factory bean with ours that uses the customized parameter name discoverer.
//     *
//     * See:
//     *  * Spring issue: https://github.com/spring-projects/spring-framework/issues/23499
//     *  * Hibernate issue: https://hibernate.atlassian.net/browse/HV-1638
//     */
//    @Primary
//    @Bean("devDefaultValidator")
//    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
//    @ConditionalOnClass(HibernateValidator::class)
//    open fun defaultValidator(): LocalValidatorFactoryBean {
//        val factoryBean = CustomLocalValidatorFactoryBean()
//        factoryBean.messageInterpolator = MessageInterpolatorFactory().getObject()
//        return factoryBean
//    }
//}