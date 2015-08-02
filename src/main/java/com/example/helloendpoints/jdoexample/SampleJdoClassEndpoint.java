package com.example.helloendpoints.jdoexample;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.datanucleus.query.JDOCursorHelper;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.List;

@Api(name = "samplejdoclassendpoint",
		namespace = @ApiNamespace(
				ownerDomain = "helloendpoints.example.com",
				ownerName = "helloendpoints.example.com",
				packagePath = "helloendpoints.example.com"
		)
)
public class SampleJdoClassEndpoint {

	/**
	 * This method lists all the entities inserted in datastore.
	 * It uses HTTP GET method and paging support.
	 *
	 * @return A CollectionResponse class containing the list of all entities
	 * persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listSampleJdoClass")
	public CollectionResponse<SampleJdoClass> listSampleJdoClass(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {

		PersistenceManager mgr = null;
		Cursor cursor = null;
		List<SampleJdoClass> execute = null;

		try {
			mgr = getPersistenceManager();
			Query query = mgr.newQuery(SampleJdoClass.class);
			if (cursorString != null && cursorString.equals("")) {
				cursor = Cursor.fromWebSafeString(cursorString);
				HashMap<String, Object> extensionMap = new HashMap<String, Object>();
				extensionMap.put(JDOCursorHelper.CURSOR_EXTENSION, cursor);
				query.setExtensions(extensionMap);
			}

			if (limit != null) {
				query.setRange(0, limit);
			}

			execute = (List<SampleJdoClass>) query.execute();
			cursor = JDOCursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (SampleJdoClass obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<SampleJdoClass> builder().setItems(execute)
				.setNextPageToken(cursorString).build();
	}

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getSampleJdoClass")
	public SampleJdoClass getSampleJdoClass(@Named("id") Long id) {
		PersistenceManager mgr = getPersistenceManager();
		SampleJdoClass entity = null;
		try {
			entity = mgr.getObjectById(SampleJdoClass.class, id);
		} finally {
			mgr.close();
		}
		return entity;
	}

	/**
	 * This inserts a new entity into App Engine datastore. If the entity already
	 * exists in the datastore, an exception is thrown.
	 * It uses HTTP POST method.
	 *
	 * @param entity the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "insertSampleJdoClass")
	public SampleJdoClass insertSampleJdoClass(SampleJdoClass entity) {
		PersistenceManager mgr = getPersistenceManager();
		try {
			if (entity.getId() != null) {
				if (containsSampleJdoClass(entity)) {
					throw new EntityExistsException("Object already exists");
				}
			}
			mgr.makePersistent(entity);
		} finally {
			mgr.close();
		}
		return entity;
	}

	/**
	 * This method is used for updating an existing entity. If the entity does not
	 * exist in the datastore, an exception is thrown.
	 * It uses HTTP PUT method.
	 *
	 * @param entity the entity to be updated.
	 * @return The updated entity.
	 */
	@ApiMethod(name = "updateSampleJdoClass")
	public SampleJdoClass updateSampleJdoClass(SampleJdoClass entity) {
		PersistenceManager mgr = getPersistenceManager();
		try {
			if (!containsSampleJdoClass(entity)) {
				throw new EntityNotFoundException("Object does not exist");
			}
			mgr.makePersistent(entity);
		} finally {
			mgr.close();
		}
		return entity;
	}

	/**
	 * This method removes the entity with primary key id.
	 * It uses HTTP DELETE method.
	 *
	 * @param id the primary key of the entity to be deleted.
	 */
	@ApiMethod(name = "removeSampleJdoClass")
	public void removeSampleJdoClass(@Named("id") Long id) {
		PersistenceManager mgr = getPersistenceManager();
		try {
			SampleJdoClass entity = mgr.getObjectById(
					SampleJdoClass.class, id);
			mgr.deletePersistent(entity);
		} finally {
			mgr.close();
		}
	}

	private boolean containsSampleJdoClass(SampleJdoClass entity) {
		PersistenceManager mgr = getPersistenceManager();
		boolean contains = true;
		try {
			mgr.getObjectById(SampleJdoClass.class, entity.getId());
		} catch (javax.jdo.JDOObjectNotFoundException ex) {
			contains = false;
		} finally {
			mgr.close();
		}
		return contains;
	}

	private static PersistenceManager getPersistenceManager() {
		return PMF.get().getPersistenceManager();
	}

}
