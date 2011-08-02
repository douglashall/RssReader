DataSource_sampleataSource {
    pooled = true
    driverClassName = "org.hsqldb.jdbcDriver"
    username = "sa"
    password = ""
}
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = true
    cache.provider_class = 'net.sf.ehcache.hibernate.EhCacheProvider'
}
// environment specific settings
environments {
    development {
        dataSource {
            dbCreate = "update" // one of 'create', 'create-drop','update'
			url = ""
			driverClassName = "oracle.jdbc.OracleDriver"
			dialect = "org.hibernate.dialect.OracleDialect"
			username = ""
			password = ""
        }
    }
    test {
        dataSource {
            dbCreate = "update"
            url = "jdbc:hsqldb:mem:testDb"
        }
    }
	qa {
		dataSource {
			dbCreate = "update" // one of 'create', 'create-drop','update'
			url = ""
			driverClassName = "oracle.jdbc.OracleDriver"
			dialect = "org.hibernate.dialect.OracleDialect"
			username = ""
			password = ""
		}
	}
    production {
        dataSource {
            dbCreate = "update"
            url = "jdbc:hsqldb:file:prodDb;shutdown=true"
        }
    }
}
