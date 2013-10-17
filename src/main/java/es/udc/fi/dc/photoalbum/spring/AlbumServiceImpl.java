package es.udc.fi.dc.photoalbum.spring;

import org.springframework.transaction.annotation.Transactional;

import es.udc.fi.dc.photoalbum.hibernate.Album;
import es.udc.fi.dc.photoalbum.hibernate.AlbumDao;

import java.util.ArrayList;

@Transactional
public class AlbumServiceImpl implements AlbumService {

	private AlbumDao albumDao;

	public AlbumDao getAlbumDao() {
		return this.albumDao;
	}

	public void setAlbumDao(AlbumDao albumDao) {
		this.albumDao = albumDao;
	}

	public void create(Album album) {
		albumDao.create(album);
	}

	public void delete(Album album) {
		albumDao.delete(album);
	}

	public Album getAlbum(String name, int userId) {
		return albumDao.getAlbum(name, userId);
	}

	public void rename(Album album, String newName) {
		albumDao.rename(album, newName);
	}

	public Album getById(Integer id) {
		return albumDao.getById(id);
	}

	public ArrayList<Album> getAlbums(Integer id) {
		return albumDao.getAlbums(id);
	}

	public ArrayList<Album> getPublicAlbums() {
		return albumDao.getPublicAlbums();
	}

	public void changePrivacyLevel(Album album, String privacyLevel) {
		albumDao.changePrivacyLevel(album, privacyLevel);
	}

	public ArrayList<Album> getAlbumsSharedWith(Integer id, String ownerEmail) {
		return albumDao.getAlbumsSharedWith(id, ownerEmail);
	}

	public Album getSharedAlbum(String albumName, int userSharedToId,
			String userSharedEmail) {
		return albumDao.getSharedAlbum(albumName, userSharedToId,
				userSharedEmail);
	}

	public ArrayList<Album> getAlbumsByTag(int userId, String tag) {
		return albumDao.getAlbumsByTag(userId, tag);
	}
}
