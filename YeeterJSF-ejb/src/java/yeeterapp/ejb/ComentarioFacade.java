/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yeeterapp.ejb;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import yeeterapp.entity.Comentario;
import yeeterapp.entity.Post;

/**
 *
 * @author jugr9
 */
@Stateless
public class ComentarioFacade extends AbstractFacade<Comentario> {

    private static final Logger LOG = Logger.getLogger(UsuarioFacade.class.getName());
    
    @PersistenceContext(unitName = "YeeterApp-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ComentarioFacade() {
        super(Comentario.class);
    }
    
    public List<Comentario> queryPostFeed(Post post){
        Query q = this.em.createNamedQuery("Comentario.findByPost");
        q.setParameter("post", post);
        try{
            return q.getResultList();
        }catch(NoResultException e){
            LOG.log(Level.WARNING, "Se ha producido una excepción", e.getLocalizedMessage());
            return null;
        }
    }
}
