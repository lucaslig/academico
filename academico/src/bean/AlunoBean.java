package bean;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import util.Constantes;
import RN.AlunoRN;
import RN.OrientadorRN;
import modelo.Aluno;
import modelo.Orientador;
@ManagedBean
@SessionScoped
public class AlunoBean 
{
	private Aluno aluno = new Aluno();
	private Constantes constantes = new Constantes();
	private byte sexo;
	private List<Orientador> orientadores;
	private List<Aluno> listaAlunos;
	private String nomeOrientadorSelecionado;
	private long idOrientadorSelecionado;

	public Aluno getAluno() 
	{
		return aluno;
	}

	public void setAluno(Aluno aluno) 
	{
		this.aluno = aluno;
	}
	
	public List<Orientador> getOrientadores() throws Exception 
	{
		return OrientadorRN.listar();
	}
	
	public Constantes getConstantes() 
	{
		return constantes;
	}
	
	public List<Aluno> getListaAlunos() throws Exception
	{
		if(this.listaAlunos == null)
		{
			this.listaAlunos = AlunoRN.listar();
		}
		return this.listaAlunos;
	}
	
	public void gravarAluno()
	{
		try 
		{
			if(aluno.getOrientador() != null)
			{
			this.aluno.setSexo(sexo);
			AlunoRN.salvar(aluno);
			novo();
			FacesContext.getCurrentInstance().addMessage("aluno", new FacesMessage("Aluno Salvo com Sucesso!"));
			}
			else
			{
				FacesContext.getCurrentInstance().addMessage("aluno", new FacesMessage("Escolha um Orientador."));
			}
		} catch (Exception e) 
		{
			FacesContext.getCurrentInstance().addMessage("aluno", new FacesMessage("Erro a Salvar Aluno"));
		}
		
	}
	
	public void excluirAluno()
	{
		try 
		{
			AlunoRN.deletar(aluno);
			this.listaAlunos = null;
			this.novo();
		} 
		catch (Exception e) 
		{
			FacesContext.getCurrentInstance().addMessage("aluno", new FacesMessage("Erro ao Excluir Aluno"));
		}
		FacesContext.getCurrentInstance().addMessage("aluno", new FacesMessage("Aluno Excluido com Sucesso!"));
	}
	
	public void novo()
	{
		this.aluno = new Aluno();
		this.nomeOrientadorSelecionado = null;
	}
	
	public String editar()
	{
		this.sexo = aluno.getSexo();
		return "aluno?faces-redirect=true";
	}

	public void gravarOrientador() throws Exception
	{
		if(this.idOrientadorSelecionado > 0){
		Orientador orientador = OrientadorRN.buscarOrientadorID(this.idOrientadorSelecionado);
		this.aluno.setOrientador(orientador);
		this.nomeOrientadorSelecionado = orientador.getNome();
		}
		else
		{
			FacesContext.getCurrentInstance().addMessage("aluno", new FacesMessage("Selecione um orientador."));
		}
	}
	
	public String novoOrientador(){
		
		return "orientador?faces-redirect=true";
	}

	public byte getSexo() {
		return sexo;
	}

	public void setSexo(byte sexo) {
		this.sexo = sexo;
	}

	public String getNomeOrientadorSelecionado() {
		return nomeOrientadorSelecionado;
	}

	public void setNomeOrientadorSelecionado(String nomeOrientadorSelecionado) {
		this.nomeOrientadorSelecionado = nomeOrientadorSelecionado;
	}

	public long getIdOrientadorSelecionado() {
		return idOrientadorSelecionado;
	}

	public void setIdOrientadorSelecionado(long idOrientadorSelecionado) {
		this.idOrientadorSelecionado = idOrientadorSelecionado;
	}
	
	
}
